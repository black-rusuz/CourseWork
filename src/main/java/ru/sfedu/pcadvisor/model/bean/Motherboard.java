package ru.sfedu.pcadvisor.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;

import java.util.Objects;

public class Motherboard extends Part {
    @Attribute
    @CsvBindByPosition(position = 3)
    private String socket = "";

    @Attribute
    @CsvBindByPosition(position = 4)
    private int ddrVersion;

    public Motherboard() {
    }

    public Motherboard(long id, String name, int price, String socket, int ddrVersion) {
        super(id, name, price);
        setSocket(socket);
        setDdrVersion(ddrVersion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Motherboard that)) return false;
        if (!super.equals(o)) return false;
        return getDdrVersion() == that.getDdrVersion()
                && Objects.equals(getSocket(), that.getSocket());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSocket(), getDdrVersion());
    }

    @Override
    public String toString() {
        return "Motherboard{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", socket='" + getSocket() + '\'' +
                ", ddrVersion=" + getDdrVersion() +
                '}';
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public int getDdrVersion() {
        return ddrVersion;
    }

    public void setDdrVersion(int ddrVersion) {
        this.ddrVersion = ddrVersion;
    }
}
