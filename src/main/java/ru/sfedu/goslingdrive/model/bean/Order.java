package ru.sfedu.goslingdrive.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import ru.sfedu.goslingdrive.utils.converters.PartsConverter;
import ru.sfedu.goslingdrive.utils.converters.UserConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {
    @Attribute
    @CsvBindByPosition(position = 0)
    private long id;

    @Element
    @CsvCustomBindByPosition(position = 1, converter = UserConverter.class)
    private User user = new User();

    @ElementListUnion({
            @ElementList(entry = "AutoPart", inline = true, required = false, type = AutoPart.class),
            @ElementList(entry = "BodyPart", inline = true, required = false, type = BodyPart.class),
            @ElementList(entry = "ElectricPart", inline = true, required = false, type = ElectricPart.class),
            @ElementList(entry = "RunningPart", inline = true, required = false, type = RunningPart.class),
    })
    @CsvCustomBindByPosition(position = 2, converter = PartsConverter.class)
    private List<AutoPart> parts = new ArrayList<>();

    @Attribute
    @CsvBindByPosition(position = 3)
    private double price;

    public Order() {
    }

    public Order(long id, User user, List<AutoPart> parts, double price) {
        setId(id);
        setUser(user);
        setParts(parts);
        setPrice(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return getId() == order.getId()
                && getPrice() == order.getPrice()
                && Objects.equals(getUser(), order.getUser())
                && Objects.equals(getParts(), order.getParts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getParts(), getPrice());
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + getId() +
                ", user=" + getUser() +
                ", parts=" + getParts() +
                ", price=" + getPrice() +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AutoPart> getParts() {
        return parts;
    }

    public void setParts(List<AutoPart> parts) {
        this.parts = parts;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
