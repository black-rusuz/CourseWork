package ru.sfedu.goslingdrive.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.plexus.util.CollectionUtils;
import ru.sfedu.goslingdrive.model.HistoryContent;
import ru.sfedu.goslingdrive.model.bean.*;
import ru.sfedu.goslingdrive.utils.ConfigurationUtil;
import ru.sfedu.goslingdrive.utils.Constants;
import ru.sfedu.goslingdrive.utils.MongoUtil;
import ru.sfedu.goslingdrive.utils.ReflectUtil;

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
     * Получить все детали
     *
     * @return Список всех деталей
     */
    private List<AutoPart> getAllParts() {
        List<AutoPart> parts = new ArrayList<>();
        parts.addAll(getBodyParts());
        parts.addAll(getElectricParts());
        parts.addAll(getRunningParts());
        return parts;
    }

    /**
     * Поиск деталей по названию и ВИН-номеру
     *
     * @param name Название детали
     * @param vin  ВИН-номер
     * @return Список соответствующих деталей
     */
    public List<AutoPart> searchParts(String name, String vin) {
        List<AutoPart> parts = searchByName(name);
        if (vin != null)
            parts = CollectionUtils.intersection(parts, searchByVin(vin)).stream().toList();
        log.info(Constants.FOUND_PARTS + parts.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")));
        return parts;
    }

    /**
     * Поиск деталей по названию
     *
     * @param name Название детали
     * @return Список соответствующих деталей
     */
    public List<AutoPart> searchByName(String name) {
        return getAllParts().stream()
                .filter((e) -> e.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    /**
     * Поиск деталей по ВИН-номеру
     *
     * @param vin ВИН-Номер
     * @return Список соответствующих деталей
     */
    public List<AutoPart> searchByVin(String vin) {
        return getAllParts().stream()
                .filter((e) -> vin.toLowerCase().contains(e.getVinPart().toLowerCase()))
                .toList();
    }

    /**
     * Получить последний заказ
     *
     * @return Последний заказ
     */
    private Order getLastOrder() {
        List<Order> orders = getOrders();
        Order order = new Order();
        order.setId(System.currentTimeMillis());
        if (orders.size() != 0) order = orders.get(orders.size() - 1);
        return order;
    }

    /**
     * Получить деталь
     *
     * @param id ID детали
     * @return Деталь
     */
    private Optional<AutoPart> getPart(long id) {
        AutoPart part = getBodyPart(id);
        if (part.getId() != 0) return Optional.of(part);
        part = getElectricPart(id);
        if (part.getId() != 0) return Optional.of(part);
        part = getRunningPart(id);
        if (part.getId() != 0) return Optional.of(part);
        log.info(getNotFoundMessage(AutoPart.class, id));
        return Optional.empty();
    }

    /**
     * Изменить последний сохранённый заказ
     *
     * @param action Действие с деталью
     * @param partId ID детали
     * @return Обновлённый заказ
     */
    public Optional<Order> modifyOrder(String action, long partId) {
        switch (action.toUpperCase()) {
            case Constants.ADD -> addPart(partId);
            case Constants.REMOVE -> removePart(partId);
        }
        Order order = getLastOrder();
        order.setPrice(calculateTotalPrice(order.getId()));
        updateOrder(order);
        log.info(Constants.MODIFIED_ORDER + order);
        return Optional.of(order);
    }

    /**
     * Добавить деталь в последний сохранённый заказ
     *
     * @param partId ID детали
     * @return Обновлённый заказ
     */
    public Optional<Order> addPart(long partId) {
        Order order = getLastOrder();
        List<AutoPart> parts = new ArrayList<>(order.getParts());
        Optional<AutoPart> part = getPart(partId);

        if (part.isPresent()) {
            parts.add(part.get());
            order.setParts(parts);
            updateOrder(order);
            log.info(Constants.ADDED_PART + part.get().getName());
        }
        return Optional.of(order);
    }

    /**
     * Удалить деталь из последнего сохранённого заказа
     *
     * @param partId ID детали
     * @return Обновлённый заказ
     */
    public Optional<Order> removePart(long partId) {
        Order order = getLastOrder();
        List<AutoPart> parts = new ArrayList<>(order.getParts());
        Optional<AutoPart> part = getPart(partId);

        if (part.isPresent()) {
            if (parts.contains(part.get())) {
                parts.remove(part.get());
                order.setParts(parts);
                updateOrder(order);
                log.info(Constants.REMOVED_PART + part.get().getName());
            } else {
                log.info(Constants.PART_NOT_INSTALLED + part.get().getName());
            }
        }
        return Optional.of(order);
    }

    /**
     * Подсчитать итоговую цену
     *
     * @param orderId ID заказа
     * @return Цена
     */
    public double calculateTotalPrice(long orderId) {
        List<AutoPart> parts = getOrder(orderId).getParts();
        double orderPrice = parts.stream().mapToDouble(AutoPart::getPrice).sum();
        log.info(Constants.TOTAL_PRICE + orderPrice);
        return orderPrice;
    }


    // CRUD

    public List<BodyPart> getBodyParts() {
        return getAll(BodyPart.class);
    }

    public BodyPart getBodyPart(long id) {
        return getById(BodyPart.class, id);
    }

    public long insertBodyPart(BodyPart bodyPart) {
        return insert(BodyPart.class, bodyPart);
    }

    public boolean deleteBodyPart(long id) {
        return delete(BodyPart.class, id);
    }

    public boolean updateBodyPart(BodyPart bodyPart) {
        return update(BodyPart.class, bodyPart);
    }


    public List<ElectricPart> getElectricParts() {
        return getAll(ElectricPart.class);
    }

    public ElectricPart getElectricPart(long id) {
        return getById(ElectricPart.class, id);
    }

    public long insertElectricPart(ElectricPart electricPart) {
        return insert(ElectricPart.class, electricPart);
    }

    public boolean deleteElectricPart(long id) {
        return delete(ElectricPart.class, id);
    }

    public boolean updateElectricPart(ElectricPart electricPart) {
        return update(ElectricPart.class, electricPart);
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


    public List<RunningPart> getRunningParts() {
        return getAll(RunningPart.class);
    }

    public RunningPart getRunningPart(long id) {
        return getById(RunningPart.class, id);
    }

    public long insertRunningPart(RunningPart runningPart) {
        return insert(RunningPart.class, runningPart);
    }

    public boolean deleteRunningPart(long id) {
        return delete(RunningPart.class, id);
    }

    public boolean updateRunningPart(RunningPart runningPart) {
        return update(RunningPart.class, runningPart);
    }


    public List<User> getUsers() {
        return getAll(User.class);
    }

    public User getUser(long id) {
        return getById(User.class, id);
    }

    public long insertUser(User user) {
        return insert(User.class, user);
    }

    public boolean deleteUser(long id) {
        return delete(User.class, id);
    }

    public boolean updateUser(User user) {
        return update(User.class, user);
    }
}
