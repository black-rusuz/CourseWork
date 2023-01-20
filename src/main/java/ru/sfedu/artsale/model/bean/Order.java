package ru.sfedu.artsale.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import ru.sfedu.artsale.utils.JdbcUtil;
import ru.sfedu.artsale.utils.converters.ProductConverter;
import ru.sfedu.artsale.utils.converters.UserConverter;

import java.io.Serializable;
import java.util.Objects;

@Element
public class Order implements Serializable {

    @Attribute
    @CsvBindByPosition(position = 0)
    private long id;

    @Element
    @CsvCustomBindByPosition(position = 1, converter = UserConverter.class)
    private User customer = new User();

    @Element
    @CsvCustomBindByPosition(position = 2, converter = ProductConverter.class)
    private Product product = new Product();

    public Order() {
    }

    public Order(long id, User customer, Product product) {
        setId(id);
        setCustomer(customer);
        setProduct(product);
    }

    public static String toCreateTableString() {
        return String.format(
                "CREATE TABLE IF NOT EXISTS %sorder (id LONG PRIMARY KEY, customer VARCHAR, product VARCHAR);",
                JdbcUtil.tablePrefix);
    }

    public String toInsertString() {
        return "'" + getId() + "', '"
                + new UserConverter().convertToWrite(getCustomer()) + "', '"
                + new ProductConverter().convertToWrite(getProduct()) + "'";
    }

    public String toUpdateString() {
        return "id = '" + getId() + "', "
                + "customer = '" + new UserConverter().convertToWrite(getCustomer()) + "', "
                + "product = '" + new ProductConverter().convertToWrite(getProduct()) + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return getId() == order.getId()
                && Objects.equals(getCustomer(), order.getCustomer())
                && Objects.equals(getProduct(), order.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomer(), getProduct());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + getId() +
                ", customer=" + getCustomer() +
                ", product=" + getProduct() +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
