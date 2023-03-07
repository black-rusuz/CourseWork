package ru.sfedu.trainpick.utils.converters;

import com.opencsv.bean.AbstractBeanField;
import ru.sfedu.trainpick.model.bean.Train;
import ru.sfedu.trainpick.utils.Constants;

import java.util.List;
import java.util.stream.Collectors;

public class TrainConverter extends AbstractBeanField<Train, String> {
    private static final String fieldsDelimiter = Constants.FIELDS_DELIMITER;

    public static Train fromString(String string) {
        String[] parsed = string.split(fieldsDelimiter);
        return new Train(Long.parseLong(parsed[0]), parsed[1], parsed[2], parsed[3], parsed[4], Double.parseDouble(parsed[5])
        );
    }

    public static String toString(Object object) {
        Train train = (Train) object;
        List<Object> params = List.of(train.getId(), train.getFrom(), train.getTo(), train.getDeparture(), train.getArrival(), train.getPrice());
        return params.stream().map(Object::toString).collect(Collectors.joining(fieldsDelimiter));
    }

    @Override
    public Train convert(String string) {
        return fromString(string);
    }

    @Override
    public String convertToWrite(Object object) {
        return toString(object);
    }
}
