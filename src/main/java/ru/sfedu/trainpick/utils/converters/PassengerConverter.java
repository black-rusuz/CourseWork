package ru.sfedu.trainpick.utils.converters;

import com.opencsv.bean.AbstractBeanField;
import ru.sfedu.trainpick.model.bean.ChildPassenger;
import ru.sfedu.trainpick.model.bean.DiscountPassenger;
import ru.sfedu.trainpick.model.bean.Passenger;
import ru.sfedu.trainpick.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PassengerConverter extends AbstractBeanField<Passenger, String> {
    private static final String fieldsDelimiter = Constants.FIELDS_DELIMITER;

    public static Passenger fromString(String string) {
        String[] parsed = string.split(fieldsDelimiter);
        return switch (parsed.length) {
            case 4 -> new Passenger(Long.parseLong(parsed[0]), parsed[1], parsed[2], parsed[3]);
            case 5 -> new ChildPassenger(Long.parseLong(parsed[0]), parsed[1], parsed[2], parsed[3], Boolean.parseBoolean(parsed[4]));
            case 6 -> new DiscountPassenger(Long.parseLong(parsed[0]), parsed[1], parsed[2], parsed[3], Double.parseDouble(parsed[4]), parsed[5]);
            default -> new Passenger();
        };
    }

    public static String toString(Object object) {
        Passenger passenger = (Passenger) object;
        List<Object> params = new ArrayList<>(List.of(passenger.getId(), passenger.getFullName(), passenger.getBirthDate(), passenger.getDocument()));

        if (object instanceof ChildPassenger instance) {
            params.add(instance.getNoPlace());
        } else if (object instanceof DiscountPassenger instance) {
            params.add(instance.getDiscount());
            params.add(instance.getReason());
        }
        return params.stream().map(Object::toString).collect(Collectors.joining(fieldsDelimiter));
    }

    @Override
    public Passenger convert(String string) {
        return fromString(string);
    }

    @Override
    public String convertToWrite(Object object) {
        return toString(object);
    }
}
