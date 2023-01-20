package ru.sfedu.artsale.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import ru.sfedu.artsale.utils.JdbcUtil;

import java.io.Serializable;
import java.util.Objects;

@Element
public class Product implements Serializable {

    @Attribute
    @CsvBindByPosition(position = 0)
    private long id;

    @Element
    @CsvBindByPosition(position = 1)
    private String name = "";

    @Element
    @CsvBindByPosition(position = 2)
    private double price;

    public Product() {
    }

    public Product(long id, String name, double price) {
        setId(id);
        setName(name);
        setPrice(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return getId() == product.getId()
                && Double.compare(product.getPrice(), getPrice()) == 0
                && Objects.equals(getName(), product.getName());
    }

    public static String toCreateTableString() {
        return String.format(
                "CREATE TABLE IF NOT EXISTS %sproduct (id LONG PRIMARY KEY, name VARCHAR, price NUMERIC);",
                JdbcUtil.tablePrefix);
    }

    public String toInsertString() {
        return "'" + getId() + "', '"
                + getName() + "', '"
                + getPrice() + "'";
    }

    public String toUpdateString() {
        return "id = '" + getId() + "', "
                + "name = '" + getName() + "', "
                + "price = '" + getPrice() + "'";
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
