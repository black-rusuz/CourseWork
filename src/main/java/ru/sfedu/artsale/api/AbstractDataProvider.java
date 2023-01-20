package ru.sfedu.artsale.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.artsale.Constants;
import ru.sfedu.artsale.model.HistoryContent;
import ru.sfedu.artsale.model.Result;
import ru.sfedu.artsale.model.bean.*;
import ru.sfedu.artsale.utils.ConfigurationUtil;
import ru.sfedu.artsale.utils.MongoUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractDataProvider {
    protected final Logger log = LogManager.getLogger(this.getClass());
    private final boolean MONGO_ENABLE = Boolean.parseBoolean(ConfigurationUtil.getConfigurationEntry(Constants.MONGO_ENABLE));
    private final String MONGO_ACTOR = ConfigurationUtil.getConfigurationEntry(Constants.MONGO_ACTOR);

    protected AbstractDataProvider() throws IOException {
    }

    // Service
    protected void sendLogs(String methodName, Object bean, Result result) {
        HistoryContent historyContent = new HistoryContent(UUID.randomUUID(),
                this.getClass().getSimpleName(),
                LocalDateTime.now().toString(),
                MONGO_ACTOR,
                methodName,
                MongoUtil.objectToString(bean),
                result);
        if (MONGO_ENABLE) MongoUtil.saveToLog(historyContent);
    }

    /**
     * Показать доступные товары и заказать выбранный
     *
     * @param filter    Товары без категории, наборы для творчества, готовые произведения искусства или всё подряд
     *                  (Products/CreationKits/EndProducts/Any)
     * @param productId ID товара для заказа
     * @return Список доступных товаров
     */
    public List<Product> viewProducts(String filter, long productId) {
        if (productId != 0) {
            orderProduct(productId);
            showRelated(productId);
        }
        return filterView(filter);
    }

    /**
     * Фильтр товаров
     *
     * @param filter Товары без категории, наборы для творчества, готовые произведения искусства или всё подряд
     *               (Product/CreationKit/EndProduct/Any)
     * @return Отфильтрованный список доступных товаров
     */
    public List<Product> filterView(String filter) {
        List<Product> allProducts = new ArrayList<>();
        allProducts.addAll(getProducts());
        allProducts.addAll(getCreationKits());
        allProducts.addAll(getEndProducts());

        List filteredProducts = switch (filter.toLowerCase()) {
            case (Constants.PRODUCT) -> getProducts();
            case (Constants.CREATIONKIT) -> getCreationKits();
            case (Constants.ENDPRODUCT) -> getEndProducts();
            default -> allProducts;
        };

        log.info(Constants.PRODUCTS + filteredProducts.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")));
        return filteredProducts;
    }

    /**
     * Заказ выбранного товара
     *
     * @param productId ID товара
     * @return Сформированный заказ для пользователя, чьи контактные данные были оставлены последними
     */
    public Optional<Order> orderProduct(long productId) {
        User lastUser = getUsers().get(getUsers().size() - 1);
        Order order = new Order(System.currentTimeMillis(), lastUser, getProduct(productId));
        insertOrder(order);

        log.info(Constants.ORDER + order);
        return Optional.of(order);
    }

    /**
     * Просмотр рекомендуемых к покупке товаров
     *
     * @param productId ID товара
     * @return Список товаров, которые пользователи покупали вместе с выбранным
     */
    public Set<Product> showRelated(long productId) {
        Set<Product> relatedProducts = new HashSet<>();
        Product target = getProduct(productId);
        List<Order> orders = getOrders();
        Set<Long> userIds = orders.stream().map(order -> order.getCustomer().getId()).collect(Collectors.toSet());

        userIds.forEach(id -> {
            List<Order> userOrders = orders.stream().filter(order -> order.getCustomer().getId() == id).toList();
            Set<Product> orderedProducts = userOrders.stream().map(Order::getProduct).collect(Collectors.toSet());

            if (orderedProducts.contains(target)) {
                orderedProducts.remove(target);
                relatedProducts.addAll(orderedProducts);
            }
        });

        if (!relatedProducts.isEmpty()) {
            log.info(Constants.RELATED + relatedProducts.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("\n")));
        }
        return relatedProducts;
    }

    /**
     * Просмотр информации о пользователе.
     *
     * @param userId    ID пользователя
     * @param calculate true для подсчёта итоговой суммы всех покупок пользователя
     * @return пользовательские контактные данные
     */
    public Optional<User> viewUserData(long userId, boolean calculate) {
        User requestedUser = getUser(userId);
        log.info(Constants.USER + requestedUser);
        viewUserOrders(userId);
        if (calculate) calculateAmount(userId);
        return Optional.of(requestedUser);
    }

    /**
     * Просмотр товаров, заказанных пользователем с указанным ID
     *
     * @param userId ID пользователя
     * @return список заказов пользователя
     */
    public List<Order> viewUserOrders(long userId) {
        List<Order> userOrders = getOrders();
        userOrders.removeIf(order -> order.getCustomer().getId() != userId);

        log.info(Constants.USER_ORDERS + userOrders.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")));
        return userOrders;
    }

    /**
     * Подсчёт итоговой суммы, потраченной на все заказы выбранного пользователя
     *
     * @param userId ID пользователя
     * @return итоговая сумма
     */
    public double calculateAmount(long userId) {
        List<Order> orders = getOrders().stream().filter(order -> order.getCustomer().getId() == userId).toList();
        double amount = orders.stream().mapToDouble(order -> order.getProduct().getPrice()).sum();

        log.info(Constants.USER_AMOUNT + amount);
        return amount;
    }

    // CRUD
    public abstract List<User> getUsers();

    public abstract User getUser(long id);

    public abstract long insertUser(User user);

    public abstract boolean deleteUser(long id);

    public abstract boolean updateUser(User user);


    public abstract List<Product> getProducts();

    public abstract Product getProduct(long id);

    public abstract long insertProduct(Product product);

    public abstract boolean deleteProduct(long id);

    public abstract boolean updateProduct(Product product);


    public abstract List<CreationKit> getCreationKits();

    public abstract CreationKit getCreationKit(long id);

    public abstract long insertCreationKit(CreationKit creationKit);

    public abstract boolean deleteCreationKit(long id);

    public abstract boolean updateCreationKit(CreationKit creationKit);


    public abstract List<EndProduct> getEndProducts();

    public abstract EndProduct getEndProduct(long id);

    public abstract long insertEndProduct(EndProduct endProduct);

    public abstract boolean deleteEndProduct(long id);

    public abstract boolean updateEndProduct(EndProduct endProduct);


    public abstract List<Order> getOrders();

    public abstract Order getOrder(long id);

    public abstract long insertOrder(Order order);

    public abstract boolean deleteOrder(long id);

    public abstract boolean updateOrder(Order order);
}
