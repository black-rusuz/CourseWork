package ru.sfedu.photohaven.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.photohaven.model.HistoryContent;
import ru.sfedu.photohaven.model.bean.*;
import ru.sfedu.photohaven.utils.ConfigurationUtil;
import ru.sfedu.photohaven.utils.Constants;
import ru.sfedu.photohaven.utils.MongoUtil;
import ru.sfedu.photohaven.utils.ReflectUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.collections.ListUtils.intersection;

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

    List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        products.addAll(getAccessories());
        products.addAll(getCameras());
        products.addAll(getMaterials());
        return products;
    }

    public List<Product> watchProducts(String type, String pattern, int maxPrice) {
        List<Product> products = getProducts();
        if (type != null) products = intersection(products, filterByType(type));
        if (pattern != null) products = intersection(products, searchPattern(pattern));
        if (maxPrice != 0) products = intersection(products, setMaxPrice(maxPrice));
        return products;
    }

    public List<Product> filterByType(String type) {
        List<Product> products = new ArrayList<>();
        if (type.equalsIgnoreCase(Accessory.class.getSimpleName())) products.addAll(getAccessories());
        if (type.equalsIgnoreCase(Camera.class.getSimpleName())) products.addAll(getCameras());
        if (type.equalsIgnoreCase(Material.class.getSimpleName())) products.addAll(getMaterials());
        return products;
    }

    public List<Product> searchPattern(String pattern) {
        return getProducts().stream().filter(e -> e.getName().equalsIgnoreCase(pattern)).toList();
    }

    public List<Product> setMaxPrice(int maxPrice) {
        return getProducts().stream().filter(e -> e.getPrice() <= maxPrice).toList();
    }

    Basket getLastBasket() {
        List<Basket> baskets = getBaskets();
        Basket basket;
        if (baskets.isEmpty()) {
            basket = new Basket();
            insertBasket(basket);
        }
        basket = baskets.get(baskets.size() - 1);
        return basket;
    }

    Optional<Product> getProduct(long productId) {
        Product product = getAccessory(productId);
        if (product.getId() != 0) return Optional.of(product);
        product = getCamera(productId);
        if (product.getId() != 0) return Optional.of(product);
        product = getMaterial(productId);
        if (product.getId() != 0) return Optional.of(product);
        log.info(getNotFoundMessage(Product.class, productId));
        return Optional.empty();
    }

    public Optional<Basket> manageBasket(String action, long productId) {
        Basket basket = getLastBasket();
        switch (action) {
            case Constants.ADD -> addProduct(basket.getId(), productId);
            case Constants.REMOVE -> removeProduct(basket.getId(), productId);
        }
        updateBasket(basket);
        getTotalPrice(basket.getId());
        return Optional.of(basket);
    }

    public int getTotalPrice(long basketId) {
        return getBasket(basketId).getProducts().stream().mapToInt(Product::getPrice).sum();
    }

    public Optional<Basket> addProduct(long basketId, long productId) {
        Basket basket = getBasket(basketId);
        List<Product> products = new ArrayList<>(basket.getProducts());
        Optional<Product> product = getProduct(productId);

        if (product.isPresent()) {
            products.add(product.get());
            basket.setProducts(products);
            updateBasket(basket);
            log.info(Constants.ADDED_PART + product.get().getName());
        }
        return Optional.of(basket);
    }

    public Optional<Basket> removeProduct(long basketId, long productId) {
        Basket basket = getBasket(basketId);
        List<Product> products = new ArrayList<>(basket.getProducts());
        Optional<Product> product = getProduct(productId);

        if (product.isPresent()) {
            if (products.contains(product.get())) {
                products.remove(product.get());
                basket.setProducts(products);
                updateBasket(basket);
                log.info(Constants.REMOVED_PART + product.get().getName());
            } else {
                log.info(Constants.PART_NOT_INSTALLED + product.get().getName());
            }
        }
        return Optional.of(basket);
    }

    // CRUD

    public List<Accessory> getAccessories() {
        return getAll(Accessory.class);
    }

    public Accessory getAccessory(long id) {
        return getById(Accessory.class, id);
    }

    public long insertAccessory(Accessory accessory) {
        return insert(Accessory.class, accessory);
    }

    public boolean deleteAccessory(long id) {
        return delete(Accessory.class, id);
    }

    public boolean updateAccessory(Accessory accessory) {
        return update(Accessory.class, accessory);
    }


    public List<Basket> getBaskets() {
        return getAll(Basket.class);
    }

    public Basket getBasket(long id) {
        return getById(Basket.class, id);
    }

    public long insertBasket(Basket basket) {
        return insert(Basket.class, basket);
    }

    public boolean deleteBasket(long id) {
        return delete(Basket.class, id);
    }

    public boolean updateBasket(Basket basket) {
        return update(Basket.class, basket);
    }


    public List<Camera> getCameras() {
        return getAll(Camera.class);
    }

    public Camera getCamera(long id) {
        return getById(Camera.class, id);
    }

    public long insertCamera(Camera camera) {
        return insert(Camera.class, camera);
    }

    public boolean deleteCamera(long id) {
        return delete(Camera.class, id);
    }

    public boolean updateCamera(Camera camera) {
        return update(Camera.class, camera);
    }


    public List<Material> getMaterials() {
        return getAll(Material.class);
    }

    public Material getMaterial(long id) {
        return getById(Material.class, id);
    }

    public long insertMaterial(Material material) {
        return insert(Material.class, material);
    }

    public boolean deleteMaterial(long id) {
        return delete(Material.class, id);
    }

    public boolean updateMaterial(Material material) {
        return update(Material.class, material);
    }
}
