package ru.sfedu.photohaven.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;

public class Camera extends Product {
    @Attribute
    @CsvBindByPosition(position = 3)
    private boolean isDigital;

    public Camera() {
    }

    public Camera(long id, String name, int price, boolean isDigital) {
        super(id, name, price);
        setIsDigital(isDigital);
    }

    @Override
    public String toString() {
        return "Camera{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", isDigital=" + getIsDigital() +
                '}';
    }

    public boolean getIsDigital() {
        return isDigital;
    }

    public void setIsDigital(boolean digital) {
        isDigital = digital;
    }
}
