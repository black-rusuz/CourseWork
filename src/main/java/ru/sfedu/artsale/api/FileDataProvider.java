package ru.sfedu.artsale.api;

import ru.sfedu.artsale.Constants;
import ru.sfedu.artsale.model.bean.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class FileDataProvider extends AbstractDataProvider {
    protected String filename;

    public FileDataProvider() throws IOException {
    }

    /**
     * Reads bean list from file.
     *
     * @param type class that needed to read
     * @param <T>  generic class of list entries
     * @return list of read beans
     */
    protected abstract <T> List<T> read(Class<T> type);

    /**
     * Writes list of any beans to file.
     *
     * @param list list of beans to write
     * @param <T>  generic class of list entries
     * @return writing Result (Success/Warning/Error and message)
     */
    protected abstract <T> boolean write(List<T> list, Class<T> type, String methodName);

    /**
     * Creates File variable to read from/write in. Creates file in filesystem if not exists.
     */
    protected File initFile(String fullFileName) throws IOException {
        File file = new File(fullFileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        return file;
    }

    /**
     * Generates full file name by bean.
     *
     * @param type          bean to work with
     * @param <T>           generic class of bean
     * @return full filename string
     */
    protected <T> String getFileName(Class<T> type) {
        return String.format(filename, type.getSimpleName());
    }

    @Override
    public List<User> getUsers() {
        return read(User.class);
    }

    @Override
    public User getUser(long id) {
        List<User> list = getUsers().stream().filter(e -> e.getId() == id).toList();
        return list.isEmpty() ? new User() : list.get(0);
    }

    @Override
    public long insertUser(User user) {
        long id = user.getId();
        if (getUser(id).getId() != 0)
            user.setId(System.currentTimeMillis());
        List<User> list = getUsers();
        list.add(user);
        write(list, User.class, Constants.METHOD_NAME_APPEND);
        return user.getId();
    }

    @Override
    public boolean deleteUser(long id) {
        if (getUser(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        List<User> list = getUsers();
        list.removeIf(e -> e.getId() == id);
        return write(list, User.class, Constants.METHOD_NAME_DELETE);
    }

    @Override
    public boolean updateUser(User user) {
        long id = user.getId();
        if (getUser(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        List<User> list = getUsers();
        list.set(list.indexOf(getUser(user.getId())), user);
        return write(list, User.class, Constants.METHOD_NAME_UPDATE);
    }

    @Override
    public List<Product> getProducts() {
        return read(Product.class);
    }

    @Override
    public Product getProduct(long id) {
        List<Product> list = getProducts().stream().filter(e -> e.getId() == id).toList();
        return list.isEmpty() ? new Product() : list.get(0);
    }

    @Override
    public long insertProduct(Product product) {
        long id = product.getId();
        if (getProduct(id).getId() != 0)
            product.setId(System.currentTimeMillis());
        List<Product> list = getProducts();
        list.add(product);
        write(list, Product.class, Constants.METHOD_NAME_APPEND);
        return product.getId();
    }

    @Override
    public boolean deleteProduct(long id) {
        if (getProduct(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        List<Product> list = getProducts();
        list.removeIf(e -> e.getId() == id);
        return write(list, Product.class, Constants.METHOD_NAME_DELETE);
    }

    @Override
    public boolean updateProduct(Product product) {
        long id = product.getId();
        if (getProduct(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        List<Product> list = getProducts();
        list.set(list.indexOf(getProduct(product.getId())), product);
        return write(list, Product.class, Constants.METHOD_NAME_UPDATE);
    }

    @Override
    public List<CreationKit> getCreationKits() {
        return read(CreationKit.class);
    }

    @Override
    public CreationKit getCreationKit(long id) {
        List<CreationKit> list = getCreationKits().stream().filter(e -> e.getId() == id).toList();
        return list.isEmpty() ? new CreationKit() : list.get(0);
    }

    @Override
    public long insertCreationKit(CreationKit creationKit) {
        long id = creationKit.getId();
        if (getCreationKit(id).getId() != 0)
            creationKit.setId(System.currentTimeMillis());
        List<CreationKit> list = getCreationKits();
        list.add(creationKit);
        write(list, CreationKit.class, Constants.METHOD_NAME_APPEND);
        return creationKit.getId();
    }

    @Override
    public boolean deleteCreationKit(long id) {
        if (getCreationKit(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        List<CreationKit> list = getCreationKits();
        list.removeIf(e -> e.getId() == id);
        return write(list, CreationKit.class, Constants.METHOD_NAME_DELETE);
    }

    @Override
    public boolean updateCreationKit(CreationKit creationKit) {
        long id = creationKit.getId();
        if (getCreationKit(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        List<CreationKit> list = getCreationKits();
        list.set(list.indexOf(getCreationKit(creationKit.getId())), creationKit);
        return write(list, CreationKit.class, Constants.METHOD_NAME_UPDATE);
    }

    @Override
    public List<EndProduct> getEndProducts() {
        return read(EndProduct.class);
    }

    @Override
    public EndProduct getEndProduct(long id) {
        List<EndProduct> list = getEndProducts().stream().filter(e -> e.getId() == id).toList();
        return list.isEmpty() ? new EndProduct() : list.get(0);
    }

    @Override
    public long insertEndProduct(EndProduct endProduct) {
        long id = endProduct.getId();
        if (getEndProduct(id).getId() != 0)
            endProduct.setId(System.currentTimeMillis());
        List<EndProduct> list = getEndProducts();
        list.add(endProduct);
        write(list, EndProduct.class, Constants.METHOD_NAME_APPEND);
        return endProduct.getId();
    }

    @Override
    public boolean deleteEndProduct(long id) {
        if (getEndProduct(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        List<EndProduct> list = getEndProducts();
        list.removeIf(e -> e.getId() == id);
        return write(list, EndProduct.class, Constants.METHOD_NAME_DELETE);
    }

    @Override
    public boolean updateEndProduct(EndProduct endProduct) {
        long id = endProduct.getId();
        if (getEndProduct(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        List<EndProduct> list = getEndProducts();
        list.set(list.indexOf(getEndProduct(endProduct.getId())), endProduct);
        return write(list, EndProduct.class, Constants.METHOD_NAME_UPDATE);
    }

    @Override
    public List<Order> getOrders() {
        return read(Order.class);
    }

    @Override
    public Order getOrder(long id) {
        List<Order> list = getOrders().stream().filter(e -> e.getId() == id).toList();
        return list.isEmpty() ? new Order() : list.get(0);
    }

    @Override
    public long insertOrder(Order order) {
        long id = order.getId();
        if (getOrder(id).getId() != 0)
            order.setId(System.currentTimeMillis());
        List<Order> list = getOrders();
        list.add(order);
        write(list, Order.class, Constants.METHOD_NAME_APPEND);
        return order.getId();
    }

    @Override
    public boolean deleteOrder(long id) {
        if (getOrder(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        List<Order> list = getOrders();
        list.removeIf(e -> e.getId() == id);
        return write(list, Order.class, Constants.METHOD_NAME_DELETE);
    }

    @Override
    public boolean updateOrder(Order order) {
        long id = order.getId();
        if (getOrder(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        List<Order> list = getOrders();
        list.set(list.indexOf(getOrder(order.getId())), order);
        return write(list, Order.class, Constants.METHOD_NAME_UPDATE);
    }
}
