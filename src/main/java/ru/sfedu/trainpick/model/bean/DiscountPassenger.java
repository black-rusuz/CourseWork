package ru.sfedu.trainpick.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.Objects;

public class DiscountPassenger extends Passenger {
    @Attribute
    @CsvBindByPosition(position = 4)
    private double discount;

    @Attribute
    @CsvBindByPosition(position = 5)
    private String reason = "";

    public DiscountPassenger() {
    }

    public DiscountPassenger(long id, String fullName, String birthDate, String document, double discount, String reason) {
        super(id, fullName, birthDate, document);
        this.discount = discount;
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscountPassenger that)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(that.discount, discount) == 0 && Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), discount, reason);
    }

    @Override
    public String toString() {
        return "DiscountPassenger{" +
                "id=" + super.getId() +
                ", fullName='" + super.getFullName() + '\'' +
                ", birthDate='" + super.getBirthDate() + '\'' +
                ", document='" + super.getDocument() + '\'' +
                ", discount=" + discount +
                ", reason='" + reason + '\'' +
                '}';
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
