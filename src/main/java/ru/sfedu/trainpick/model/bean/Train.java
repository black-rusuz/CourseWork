package ru.sfedu.trainpick.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;

import java.util.Objects;

public class Train {
    @Attribute
    @CsvBindByPosition(position = 0)
    private long id;

    @Attribute
    @CsvBindByPosition(position = 1)
    private String from = "";

    @Attribute
    @CsvBindByPosition(position = 2)
    private String to = "";

    @Attribute
    @CsvBindByPosition(position = 3)
    private String departure = "";

    @Attribute
    @CsvBindByPosition(position = 4)
    private String arrival = "";

    @Attribute
    @CsvBindByPosition(position = 5)
    private double price;

    public Train() {
    }

    public Train(long id, String from, String to, String departure, String arrival, double price) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.arrival = arrival;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Train train)) return false;
        return id == train.id
                && Double.compare(train.price, price) == 0
                && Objects.equals(from, train.from) && Objects.equals(to, train.to)
                && Objects.equals(departure, train.departure)
                && Objects.equals(arrival, train.arrival);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, departure, arrival, price);
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival + '\'' +
                ", price=" + price +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
