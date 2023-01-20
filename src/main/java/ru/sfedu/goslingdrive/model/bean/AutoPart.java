package ru.sfedu.goslingdrive.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;

import java.io.Serializable;
import java.util.Objects;

public abstract class AutoPart implements Serializable {
    @Attribute
    @CsvBindByPosition(position = 0)
    private long id;

    @Attribute
    @CsvBindByPosition(position = 1)
    private String name = "";

    @Attribute
    @CsvBindByPosition(position = 2)
    private double price;

    @Attribute
    @CsvBindByPosition(position = 3)
    private String vinPart = "";

    @Attribute
    @CsvBindByPosition(position = 4)
    private int warranty;

    public AutoPart() {
    }

    public AutoPart(long id, String name, double price, String vinPart, int warranty) {
        setId(id);
        setName(name);
        setPrice(price);
        setVinPart(vinPart);
        setWarranty(warranty);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AutoPart autoPart)) return false;
        return getId() == autoPart.getId()
                && getPrice() == autoPart.getPrice()
                && getWarranty() == autoPart.getWarranty()
                && Objects.equals(getName(), autoPart.getName())
                && Objects.equals(getVinPart(), autoPart.getVinPart());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), getVinPart(), getWarranty());
    }

    @Override
    public String toString() {
        return "AutoPart{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", vinPart='" + vinPart + '\'' +
                ", warranty=" + warranty +
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

    public String getVinPart() {
        return vinPart;
    }

    public void setVinPart(String vinPart) {
        this.vinPart = vinPart;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }
}
