package ru.sfedu.pcadvisor.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;

import java.util.Objects;

public class Ram extends Part {
    @Attribute
    @CsvBindByPosition(position = 3)
    private int volumeGb;

    @Attribute
    @CsvBindByPosition(position = 4)
    private int ddrVersion;

    public Ram() {
    }

    public Ram(long id, String name, int price, int volumeGb, int ddrVersion) {
        super(id, name, price);
        setVolumeGb(volumeGb);
        setDdrVersion(ddrVersion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ram ram)) return false;
        if (!super.equals(o)) return false;
        return getVolumeGb() == ram.getVolumeGb()
                && getDdrVersion() == ram.getDdrVersion();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getVolumeGb(), getDdrVersion());
    }

    @Override
    public String toString() {
        return "Ram{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", volumeGb=" + getVolumeGb() +
                ", ddrVersion=" + getDdrVersion() +
                '}';
    }

    public int getVolumeGb() {
        return volumeGb;
    }

    public void setVolumeGb(int volumeGb) {
        this.volumeGb = volumeGb;
    }

    public int getDdrVersion() {
        return ddrVersion;
    }

    public void setDdrVersion(int ddrVersion) {
        this.ddrVersion = ddrVersion;
    }
}
