package ru.sfedu.artsale.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import ru.sfedu.artsale.utils.JdbcUtil;

import java.util.Objects;

@Element
public class EndProduct extends Product {

    @Element
    @CsvBindByPosition(position = 3)
    private int warranty;   // months

    public EndProduct() {
    }

    public EndProduct(long id, String name, double price, int warranty) {
        super(id, name, price);
        setWarranty(warranty);
    }

    public static String toCreateTableString() {
        return String.format(
                "CREATE TABLE IF NOT EXISTS %sendProduct (id LONG PRIMARY KEY, name VARCHAR, price NUMERIC, warranty INT);",
                JdbcUtil.tablePrefix);
    }

    public String toInsertString() {
        return "'" + getId() + "', '"
                + getName() + "', '"
                + getPrice() + "', '"
                + getWarranty() + "'";
    }

    public String toUpdateString() {
        return "id = '" + getId() + "', "
                + "name = '" + getName() + "', "
                + "price = '" + getPrice() + "', "
                + "warranty = '" + getWarranty() + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EndProduct that)) return false;
        if (!super.equals(o)) return false;
        return getWarranty() == that.getWarranty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getWarranty());
    }

    @Override
    public String toString() {
        return "EndProduct{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() + '\'' +
                ", warranty=" + getWarranty() +
                '}';
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }
}
