package ru.sfedu.goslingdrive.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;

import java.util.Objects;

public class ElectricPart extends AutoPart {
    @Attribute
    @CsvBindByPosition(position = 5)
    private double wattage;

    public ElectricPart() {
    }

    public ElectricPart(long id, String name, int price, String vinPart, int warranty, double wattage) {
        super(id, name, price, vinPart, warranty);
        setWattage(wattage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ElectricPart that)) return false;
        if (!super.equals(o)) return false;
        return getWattage() == that.getWattage();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getWattage());
    }

    @Override
    public String toString() {
        return "ElectricPart{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", price=" + super.getPrice() +
                ", vinPart='" + super.getVinPart() + '\'' +
                ", warranty=" + super.getWarranty() +
                ", wattage=" + getWattage() +
                '}';
    }

    public double getWattage() {
        return wattage;
    }

    public void setWattage(double wattage) {
        this.wattage = wattage;
    }
}
