package ru.sfedu.trainpick.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import ru.sfedu.trainpick.utils.converters.PassengerConverter;
import ru.sfedu.trainpick.utils.converters.TrainConverter;

import java.util.Objects;

public class Ticket {
    @Attribute
    @CsvBindByPosition(position = 0)
    private long id;

    @Element
    @CsvCustomBindByPosition(position = 1, converter = PassengerConverter.class)
    private Passenger passenger = new Passenger();

    @Element
    @CsvCustomBindByPosition(position = 2, converter = TrainConverter.class)
    private Train train = new Train();

    @Attribute
    @CsvBindByPosition(position = 3)
    private String duration = "";

    @Attribute
    @CsvBindByPosition(position = 4)
    private double cost;

    @Attribute
    @CsvBindByPosition(position = 5)
    private boolean isPaid;

    public Ticket() {
    }

    public Ticket(long id, Passenger passenger, Train train, String duration, double cost, boolean isPaid) {
        this.id = id;
        this.passenger = passenger;
        this.train = train;
        this.duration = duration;
        this.cost = cost;
        this.isPaid = isPaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket ticket)) return false;
        return id == ticket.id
                && Double.compare(ticket.cost, cost) == 0
                && isPaid == ticket.isPaid
                && Objects.equals(passenger, ticket.passenger)
                && Objects.equals(train, ticket.train)
                && Objects.equals(duration, ticket.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passenger, train, duration, cost, isPaid);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", passenger=" + passenger +
                ", train=" + train +
                ", duration='" + duration + '\'' +
                ", cost=" + cost +
                ", isPaid=" + isPaid +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
