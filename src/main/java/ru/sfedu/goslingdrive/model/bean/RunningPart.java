package ru.sfedu.goslingdrive.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;

import java.util.Objects;

public class RunningPart extends AutoPart {
    @Attribute
    @CsvBindByPosition(position = 5)
    boolean isCustom;

    public RunningPart() {
    }

    public RunningPart(long id, String name, int price, String vinPart, int warranty, boolean isCustom) {
        super(id, name, price, vinPart, warranty);
        setIsCustom(isCustom);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RunningPart that)) return false;
        if (!super.equals(o)) return false;
        return getIsCustom() == that.getIsCustom();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getIsCustom());
    }

    @Override
    public String toString() {
        return "RunningPart{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", price=" + super.getPrice() +
                ", vinPart='" + super.getVinPart() + '\'' +
                ", warranty=" + super.getWarranty() +
                ", isCustom=" + getIsCustom() +
                '}';
    }

    public boolean getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(boolean custom) {
        this.isCustom = custom;
    }
}
