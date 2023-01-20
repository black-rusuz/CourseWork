package ru.sfedu.pcadvisor.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;

import java.io.Serializable;
import java.util.Objects;

public abstract class Part implements Serializable {
    @Attribute
    @CsvBindByPosition(position = 0)
    private long id;

    @Attribute
    @CsvBindByPosition(position = 1)
    private String name = "";

    @Attribute
    @CsvBindByPosition(position = 2)
    private double price;

    public Part() {
    }

    public Part(long id, String name, int price) {
        setId(id);
        setName(name);
        setPrice(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Part part)) return false;
        return getId() == part.getId()
                && getPrice() == part.getPrice()
                && Objects.equals(getName(), part.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice());
    }

    @Override
    public String toString() {
        return "Part{" +
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
