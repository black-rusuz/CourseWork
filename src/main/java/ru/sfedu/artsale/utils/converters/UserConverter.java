package ru.sfedu.artsale.utils.converters;

import com.opencsv.bean.AbstractBeanField;
import ru.sfedu.artsale.model.bean.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter extends AbstractBeanField<User, String> {
    public static final String fieldsDelimiter = "::";

    @Override
    public User convert(String s) {
        String[] parsed = s.split(fieldsDelimiter);
        return new User(Long.parseLong(parsed[0]), parsed[1], parsed[2], parsed[3], parsed[4]);
    }

    @Override
    public String convertToWrite(Object object) {
        User user = (User) object;
        List<Object> params = List.of(user.getId(),
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                user.getAddress());
        return params.stream().map(Object::toString).collect(Collectors.joining(fieldsDelimiter));
    }
}
