package ru.sfedu.photohaven.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;

import java.util.Objects;

public class Material extends Product {
    @Attribute
    @CsvBindByPosition(position = 3)
    private int quantity;

    @Attribute
    @CsvBindByPosition(position = 4)
    private String unit = "";

    public Material() {
    }

    public Material(long id, String name, int price, int quantity, String unit) {
        super(id, name, price);
        setQuantity(quantity);
        setUnit(unit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material material)) return false;
        if (!super.equals(o)) return false;
        return getQuantity() == material.getQuantity()
                && Objects.equals(getUnit(), material.getUnit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getQuantity(), getUnit());
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", quantity=" + getQuantity() +
                ", unit='" + getUnit() + '\'' +
                '}';
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
