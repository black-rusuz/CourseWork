package ru.sfedu.artsale.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import ru.sfedu.artsale.utils.JdbcUtil;

import java.util.Objects;

@Element
public class CreationKit extends Product {

    @Element
    @CsvBindByPosition(position = 3)
    private String category = "";

    @Element
    @CsvBindByPosition(position = 4)
    private String composition = "";

    public CreationKit() {
    }

    public CreationKit(long id, String name, double price, String category, String composition) {
        super(id, name, price);
        setCategory(category);
        setComposition(composition);
    }

    public static String toCreateTableString() {
        return String.format(
                "CREATE TABLE IF NOT EXISTS %screationKit (id LONG PRIMARY KEY, name VARCHAR, price NUMERIC, category VARCHAR, composition VARCHAR);",
                JdbcUtil.tablePrefix);
    }

    public String toInsertString() {
        return "'" + getId() + "', '"
                + getName() + "', '"
                + getPrice() + "', '"
                + getCategory() + "', '"
                + getComposition() + "'";
    }

    public String toUpdateString() {
        return "id = '" + getId() + "', "
                + "name = '" + getName() + "', "
                + "price = '" + getPrice() + "', "
                + "category = '" + getCategory() + "', "
                + "composition = '" + getComposition() + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreationKit that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getCategory(), that.getCategory())
                && Objects.equals(composition, that.composition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCategory(), composition);
    }

    @Override
    public String toString() {
        return "CreationKit{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() + '\'' +
                ", category='" + getCategory() + '\'' +
                ", composition='" + getComposition() + '\'' +
                '}';
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }
}
