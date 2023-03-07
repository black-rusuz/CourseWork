package ru.sfedu.trainpick.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;

import java.util.Objects;

public class Passenger {
    @Attribute
    @CsvBindByPosition(position = 0)
    private long id;

    @Attribute
    @CsvBindByPosition(position = 1)
    private String fullName = "";

    @Attribute
    @CsvBindByPosition(position = 2)
    private String birthDate = "";

    @Attribute
    @CsvBindByPosition(position = 3)
    private String document = "";

    public Passenger() {
    }

    public Passenger(long id, String fullName, String birthDate, String document) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.document = document;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger passenger)) return false;
        return id == passenger.id
                && Objects.equals(fullName, passenger.fullName)
                && Objects.equals(birthDate, passenger.birthDate)
                && Objects.equals(document, passenger.document);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, birthDate, document);
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", document='" + document + '\'' +
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
