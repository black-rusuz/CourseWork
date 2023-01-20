package ru.sfedu.pcadvisor.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;

import java.util.Objects;

public class Cpu extends Part {
    @Attribute
    @CsvBindByPosition(position = 3)
    private int frequency;

    @Attribute
    @CsvBindByPosition(position = 4)
    private int coreCount;

    @Attribute
    @CsvBindByPosition(position = 5)
    private String socket = "";

    public Cpu() {
    }

    public Cpu(long id, String name, int price, int frequency, int coreCount, String socket) {
        super(id, name, price);
        setFrequency(frequency);
        setCoreCount(coreCount);
        setSocket(socket);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cpu cpu)) return false;
        if (!super.equals(o)) return false;
        return getFrequency() == cpu.getFrequency()
                && getCoreCount() == cpu.getCoreCount()
                && Objects.equals(getSocket(), cpu.getSocket());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFrequency(), getCoreCount(), getSocket());
    }

    @Override
    public String toString() {
        return "Cpu{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", frequency=" + getFrequency() +
                ", coreCount=" + getCoreCount() +
                ", socket='" + getSocket() + '\'' +
                '}';
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getCoreCount() {
        return coreCount;
    }

    public void setCoreCount(int coreCount) {
        this.coreCount = coreCount;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }
}
