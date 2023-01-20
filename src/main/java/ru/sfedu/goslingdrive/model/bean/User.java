package ru.sfedu.goslingdrive.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    @Attribute
    @CsvBindByPosition(position = 0)
    private long id;

    @Attribute
    @CsvBindByPosition(position = 1)
    private String fullName = "";

    @Attribute
    @CsvBindByPosition(position = 2)
    private String car = "";

    @Attribute
    @CsvBindByPosition(position = 3)
    private String address = "";

    public User() {
    }

    public User(long id, String fullName, String car, String address) {
        setId(id);
        setFullName(fullName);
        setCar(car);
        setAddress(address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getId() == user.getId()
                && Objects.equals(getFullName(), user.getFullName())
                && Objects.equals(getCar(), user.getCar())
                && Objects.equals(getAddress(), user.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFullName(), getCar(), getAddress());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", fullName='" + getFullName() + '\'' +
                ", car='" + getCar() + '\'' +
                ", address='" + getAddress() + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
