package ru.sfedu.photohaven.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;

import java.util.Objects;

public class Accessory extends Product {
    @Attribute
    @CsvBindByPosition(position = 3)
    private String description = "";

    public Accessory() {
    }

    public Accessory(long id, String name, int price, String description) {
        super(id, name, price);
        setDescription(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Accessory accessory)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getDescription(), accessory.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDescription());
    }

    @Override
    public String toString() {
        return "Accessory{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", description='" + getDescription() + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
