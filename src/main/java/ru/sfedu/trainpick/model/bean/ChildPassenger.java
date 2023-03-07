package ru.sfedu.trainpick.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;

import java.util.Objects;

public class ChildPassenger extends Passenger {
    @Attribute
    @CsvBindByPosition(position = 4)
    private boolean noPlace;

    public ChildPassenger() {
    }

    public ChildPassenger(long id, String fullName, String birthDate, String document, boolean noPlace) {
        super(id, fullName, birthDate, document);
        this.noPlace = noPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChildPassenger that)) return false;
        if (!super.equals(o)) return false;
        return noPlace == that.noPlace;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), noPlace);
    }

    @Override
    public String toString() {
        return "ChildPassenger{" +
                "id=" + super.getId() +
                ", fullName='" + super.getFullName() + '\'' +
                ", birthDate='" + super.getBirthDate() + '\'' +
                ", document='" + super.getDocument() + '\'' +
                ", noPlace=" + noPlace +
                '}';
    }

    public boolean getNoPlace() {
        return noPlace;
    }

    public void setNoPlace(boolean noPlace) {
        this.noPlace = noPlace;
    }
}
