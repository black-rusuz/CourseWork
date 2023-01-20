package ru.sfedu.artsale.api;

import ru.sfedu.artsale.Constants;
import ru.sfedu.artsale.model.Result;
import ru.sfedu.artsale.model.bean.*;
import ru.sfedu.artsale.utils.ConfigurationUtil;
import ru.sfedu.artsale.utils.JdbcUtil;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataProviderJdbc extends AbstractDataProvider {
    private final String hostname = ConfigurationUtil.getConfigurationEntry(Constants.H2_HOSTNAME);
    private final String username = ConfigurationUtil.getConfigurationEntry(Constants.H2_USERNAME);
    private final String password = ConfigurationUtil.getConfigurationEntry(Constants.H2_PASSWORD);

    public DataProviderJdbc() throws IOException {
        try {
            write(User.toCreateTableString());
            write(Product.toCreateTableString());
            write(CreationKit.toCreateTableString());
            write(EndProduct.toCreateTableString());
            write(Order.toCreateTableString());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    private <T> List<T> read(Class<T> type) {
        return read(type, JdbcUtil.selectAllFromTable(JdbcUtil.tablePrefix + type.getSimpleName()));
    }

    private <T> List<T> read(Class<T> type, long id) {
        return read(type, JdbcUtil.selectFromTableById(JdbcUtil.tablePrefix + type.getSimpleName(), id));
    }

    private <T> List<T> read(Class<T> type, String sql) {
        List<T> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(hostname, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            log.debug(sql);
            list = JdbcUtil.readData(type, resultSet);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    private void write(String sql) throws SQLException {
        Connection connection = DriverManager.getConnection(hostname, username, password);
        Statement statement = connection.createStatement();

        log.debug(sql);
        statement.executeUpdate(sql);

        connection.close();
        statement.close();
    }

    private <T> boolean write(String methodName, T bean, long id) {
        String tableName = JdbcUtil.tablePrefix + bean.getClass().getSimpleName();
        String sql = switch (methodName) {
            case Constants.METHOD_NAME_APPEND -> JdbcUtil.insertIntoTableValues(tableName, JdbcUtil.beanToInsertString(bean));
            case Constants.METHOD_NAME_DELETE -> JdbcUtil.deleteFromTableById(tableName, id);
            case Constants.METHOD_NAME_UPDATE -> JdbcUtil.updateTableSetById(tableName, JdbcUtil.beanToUpdateString(bean), id);
            default -> "";
        };

        try {
            write(sql);
            sendLogs(methodName, bean, Result.SUCCESS);
            return true;
        } catch (SQLException e) {
            log.error(e.getMessage());
            sendLogs(methodName, bean, Result.ERROR);
            return false;
        }
    }

    @Override
    public List<User> getUsers() {
        return read(User.class);
    }

    @Override
    public User getUser(long id) {
        List<User> list = read(User.class, id);
        return list.isEmpty() ? new User() : list.get(0);
    }

    @Override
    public long insertUser(User user) {
        long id = user.getId();
        if (getUser(id).getId() != 0)
            user.setId(System.currentTimeMillis());
        write(Constants.METHOD_NAME_APPEND, user, user.getId());
        return user.getId();
    }

    @Override
    public boolean deleteUser(long id) {
        if (getUser(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        return write(Constants.METHOD_NAME_DELETE, getUser(id), id);
    }

    @Override
    public boolean updateUser(User user) {
        long id = user.getId();
        if (getUser(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        return write(Constants.METHOD_NAME_UPDATE, user, user.getId());
    }

    @Override
    public List<Product> getProducts() {
        return read(Product.class);
    }

    @Override
    public Product getProduct(long id) {
        List<Product> list = read(Product.class, id);
        return list.isEmpty() ? new Product() : list.get(0);
    }

    @Override
    public long insertProduct(Product product) {
        long id = product.getId();
        if (getProduct(id).getId() != 0)
            product.setId(System.currentTimeMillis());
        write(Constants.METHOD_NAME_APPEND, product, product.getId());
        return product.getId();
    }

    @Override
    public boolean deleteProduct(long id) {
        if (getProduct(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        return write(Constants.METHOD_NAME_DELETE, getProduct(id), id);
    }

    @Override
    public boolean updateProduct(Product product) {
        long id = product.getId();
        if (getProduct(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        return write(Constants.METHOD_NAME_UPDATE, product, product.getId());
    }

    @Override
    public List<CreationKit> getCreationKits() {
        return read(CreationKit.class);
    }

    @Override
    public CreationKit getCreationKit(long id) {
        List<CreationKit> list = read(CreationKit.class, id);
        return list.isEmpty() ? new CreationKit() : list.get(0);
    }

    @Override
    public long insertCreationKit(CreationKit creationKit) {
        long id = creationKit.getId();
        if (getCreationKit(id).getId() != 0)
            creationKit.setId(System.currentTimeMillis());
        write(Constants.METHOD_NAME_APPEND, creationKit, creationKit.getId());
        return creationKit.getId();
    }

    @Override
    public boolean deleteCreationKit(long id) {
        if (getCreationKit(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        return write(Constants.METHOD_NAME_DELETE, getCreationKit(id), id);
    }

    @Override
    public boolean updateCreationKit(CreationKit creationKit) {
        long id = creationKit.getId();
        if (getCreationKit(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        return write(Constants.METHOD_NAME_UPDATE, creationKit, creationKit.getId());
    }

    @Override
    public List<EndProduct> getEndProducts() {
        return read(EndProduct.class);
    }

    @Override
    public EndProduct getEndProduct(long id) {
        List<EndProduct> list = read(EndProduct.class, id);
        return list.isEmpty() ? new EndProduct() : list.get(0);
    }

    @Override
    public long insertEndProduct(EndProduct endProduct) {
        long id = endProduct.getId();
        if (getEndProduct(id).getId() != 0)
            endProduct.setId(System.currentTimeMillis());
        write(Constants.METHOD_NAME_APPEND, endProduct, endProduct.getId());
        return endProduct.getId();
    }

    @Override
    public boolean deleteEndProduct(long id) {
        if (getEndProduct(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        return write(Constants.METHOD_NAME_DELETE, getEndProduct(id), id);
    }

    @Override
    public boolean updateEndProduct(EndProduct endProduct) {
        long id = endProduct.getId();
        if (getEndProduct(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        return write(Constants.METHOD_NAME_UPDATE, endProduct, endProduct.getId());
    }

    @Override
    public List<Order> getOrders() {
        return read(Order.class);
    }

    @Override
    public Order getOrder(long id) {
        List<Order> list = read(Order.class, id);
        return list.isEmpty() ? new Order() : list.get(0);
    }

    @Override
    public long insertOrder(Order order) {
        long id = order.getId();
        if (getOrder(id).getId() != 0)
            order.setId(System.currentTimeMillis());
        write(Constants.METHOD_NAME_APPEND, order, order.getId());
        return order.getId();
    }

    @Override
    public boolean deleteOrder(long id) {
        if (getOrder(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        return write(Constants.METHOD_NAME_DELETE, getOrder(id), id);
    }

    @Override
    public boolean updateOrder(Order order) {
        long id = order.getId();
        if (getOrder(id).getId() == 0) {
            log.warn(Constants.NOT_FOUND);
            return false;
        }
        return write(Constants.METHOD_NAME_UPDATE, order, order.getId());
    }
}
