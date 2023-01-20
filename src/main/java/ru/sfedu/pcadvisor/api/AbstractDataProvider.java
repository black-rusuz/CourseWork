package ru.sfedu.pcadvisor.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.pcadvisor.model.HistoryContent;
import ru.sfedu.pcadvisor.model.bean.*;
import ru.sfedu.pcadvisor.utils.ConfigurationUtil;
import ru.sfedu.pcadvisor.utils.Constants;
import ru.sfedu.pcadvisor.utils.MongoUtil;
import ru.sfedu.pcadvisor.utils.ReflectUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("UnusedReturnValue")
public abstract class AbstractDataProvider {
    protected final Logger log = LogManager.getLogger(this.getClass());

    private boolean MONGO_ENABLE = false;
    private String MONGO_ACTOR = "";

    public AbstractDataProvider() {
        try {
            MONGO_ENABLE = Boolean.parseBoolean(ConfigurationUtil.getConfigurationEntry(Constants.MONGO_ENABLE));
            MONGO_ACTOR = ConfigurationUtil.getConfigurationEntry(Constants.MONGO_ACTOR);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    // ABSTRACT GENERICS

    protected abstract <T> List<T> getAll(Class<T> type);

    protected abstract <T> T getById(Class<T> type, long id);

    protected abstract <T> long insert(Class<T> type, T bean);

    protected abstract <T> boolean delete(Class<T> type, long id);

    protected abstract <T> boolean update(Class<T> type, T bean);

    // SERVICE

    protected void sendLogs(String methodName, Object bean, boolean result) {
        HistoryContent historyContent = new HistoryContent(UUID.randomUUID(),
                this.getClass().getSimpleName(),
                LocalDateTime.now().toString(),
                MONGO_ACTOR,
                methodName,
                MongoUtil.objectToString(bean),
                result);
        if (MONGO_ENABLE) MongoUtil.saveToLog(historyContent);
    }

    protected <T> boolean hasSavedId(Class<T> type, long id) {
        T oldBean = getById(type, id);
        return ReflectUtil.getId(oldBean) != 0;
    }

    protected <T> String getNotFoundMessage(Class<T> type, long id) {
        return String.format(Constants.NOT_FOUND, type.getSimpleName(), id);
    }

    // USE CASES

    /**
     * Подсчёт стоимости сборки компьютера
     *
     * @param orderId ID заказа
     * @return Стоимость сборки компьютера
     */
    public double countBuildPrice(long orderId) {
        Order order = getOrder(orderId);
        double cost = order.getParts().stream().mapToDouble(Part::getPrice).sum();
        log.info(Constants.TOTAL_PRICE + cost);
        return cost;
    }

    /**
     * Сборка компьютера
     *
     * @param orderId ID заказа
     * @param action  Действие для детали
     * @param partId  ID детали
     * @return Созданный заказ
     */
    public Optional<Order> buildPc(long orderId, String action, long partId) {
        switch (action.toUpperCase()) {
            case Constants.ADD -> addPart(orderId, partId);
            case Constants.REMOVE -> removePart(orderId, partId);
        }

        Order order = orderId == 0 ? new Order() : getOrder(orderId);
        validateBuild(order.getId());
        order.setTotalPrice(countBuildPrice(order.getId()));
        updateOrder(order);

        log.info(Constants.YOUR_ORDER + order);
        return Optional.of(order);
    }

    /**
     * Получить деталь
     *
     * @param partId ID детали
     * @return Деталь
     */
    private Optional<Part> getPart(long partId) {
        Cpu cpu = getCpu(partId);
        Ram ram = getRam(partId);
        Motherboard motherboard = getMotherboard(partId);

        if (motherboard.getId() == 0 && cpu.getId() == 0 && ram.getId() == 0) {
            log.info(getNotFoundMessage(Part.class, partId));
            return Optional.empty();
        }

        List<Part> parts = List.of(cpu, ram, motherboard);
        return parts.stream().filter(e -> e.getId() != 0).findFirst();
    }

    /**
     * Добавить деталь к заказу
     *
     * @param orderId ID заказа
     * @param partId  ID детали
     * @return Обновлённый заказ
     */
    public Optional<Order> addPart(long orderId, long partId) {
        Order order = getOrder(orderId);
        Optional<Part> optionalPart = getPart(partId);

        if (optionalPart.isPresent()) {
            Part part = optionalPart.get();
            List<Part> parts = new ArrayList<>(order.getParts());

            parts.add(part);
            order.setParts(parts);
            updateOrder(order);
            log.info(Constants.ADDED_PART + part.getName());
        }

        return Optional.of(order);
    }

    /**
     * Удалить деталь из заказа
     *
     * @param orderId ID заказа
     * @param partId  ID детали
     * @return Обновлённый заказ
     */
    public Optional<Order> removePart(long orderId, long partId) {
        Order order = getOrder(orderId);
        Optional<Part> optionalPart = getPart(partId);

        if (optionalPart.isPresent()) {
            Part part = optionalPart.get();
            List<Part> parts = new ArrayList<>(order.getParts());

            if (parts.contains(part)) {
                parts.remove(part);
                order.setParts(parts);
                updateOrder(order);
                log.info(Constants.REMOVED_PART + part.getName());
            } else {
                log.info(Constants.PART_NOT_INSTALLED + part.getName());
            }
        }
        return Optional.of(order);
    }

    /**
     * Проверить сборку
     *
     * @param orderId ID заказа
     * @return True, если все необходимые детали на месте
     */
    public boolean validateBuild(long orderId) {
        Order order = getOrder(orderId);
        List<Part> parts = order.getParts();
        boolean isCorrect = parts.stream().map(Part::getClass).distinct().toArray().length == 3;
        log.info(isCorrect ? Constants.BUILD_VALID : Constants.BUILD_INVALID);
        return isCorrect;
    }

    /**
     * Найти сборку
     * @param orderId ID заказа
     * @return Соответствующий заказ
     */
    public Optional<Order> findBuild(long orderId) {
        Order order = getOrder(orderId);
        if (order.getId() == 0) {
            getNotFoundMessage(Order.class, orderId);
            return Optional.empty();
        }
        log.info(Constants.YOUR_ORDER + order);
        countBuildPrice(order.getId());
        showMissingParts(orderId);
        return Optional.of(order);
    }

    /**
     * Показать недостающие детали
     *
     * @param orderId ID заказа
     * @return Список недостающих деталей
     */
    public List<Part> showMissingParts(long orderId) {
        Order order = getOrder(orderId);
        List classes = order.getParts().stream().map(Part::getClass).distinct().toList();
        ArrayList<Part> missingParts = new ArrayList<>();

        if (!classes.contains(Cpu.class)) {
            List<Cpu> cpus = getCpus();
            missingParts.addAll(cpus);
        }
        if (!classes.contains(Motherboard.class)) {
            List<Motherboard> motherboards = getMotherboards();
            missingParts.addAll(motherboards);
        }
        if (!classes.contains(Ram.class)) {
            List<Ram> rams = getRams();
            missingParts.addAll(rams);
        }

        if (!validateBuild(orderId))
            log.info(Constants.MISSING_PARTS + missingParts.stream()
                    .map(Part::toString)
                    .collect(Collectors.joining("\n")));
        return missingParts;
    }

    // CRUD

    public List<Cpu> getCpus() {
        return getAll(Cpu.class);
    }

    public Cpu getCpu(long id) {
        return getById(Cpu.class, id);
    }

    public long insertCpu(Cpu cpu) {
        return insert(Cpu.class, cpu);
    }

    public boolean deleteCpu(long id) {
        return delete(Cpu.class, id);
    }

    public boolean updateCpu(Cpu cpu) {
        return update(Cpu.class, cpu);
    }


    public List<Motherboard> getMotherboards() {
        return getAll(Motherboard.class);
    }

    public Motherboard getMotherboard(long id) {
        return getById(Motherboard.class, id);
    }

    public long insertMotherboard(Motherboard motherboard) {
        return insert(Motherboard.class, motherboard);
    }

    public boolean deleteMotherboard(long id) {
        return delete(Motherboard.class, id);
    }

    public boolean updateMotherboard(Motherboard motherboard) {
        return update(Motherboard.class, motherboard);
    }


    public List<Order> getOrders() {
        return getAll(Order.class);
    }

    public Order getOrder(long id) {
        return getById(Order.class, id);
    }

    public long insertOrder(Order order) {
        return insert(Order.class, order);
    }

    public boolean deleteOrder(long id) {
        return delete(Order.class, id);
    }

    public boolean updateOrder(Order order) {
        return update(Order.class, order);
    }


    public List<Ram> getRams() {
        return getAll(Ram.class);
    }

    public Ram getRam(long id) {
        return getById(Ram.class, id);
    }

    public long insertRam(Ram ram) {
        return insert(Ram.class, ram);
    }

    public boolean deleteRam(long id) {
        return delete(Ram.class, id);
    }

    public boolean updateRam(Ram ram) {
        return update(Ram.class, ram);
    }
}
