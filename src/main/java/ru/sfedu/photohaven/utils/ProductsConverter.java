package ru.sfedu.photohaven.utils;

import com.opencsv.bean.AbstractBeanField;
import ru.sfedu.photohaven.model.bean.Accessory;
import ru.sfedu.photohaven.model.bean.Camera;
import ru.sfedu.photohaven.model.bean.Material;
import ru.sfedu.photohaven.model.bean.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsConverter extends AbstractBeanField<List<Product>, String> {
    private static final String fieldsDelimiter = Constants.FIELDS_DELIMITER;
    private static final String beansDelimiter = Constants.BEANS_DELIMITER;

    private static Product stringToBean(String string) {
        String[] parsed = string.split(fieldsDelimiter);
        return switch (parsed.length) {
            case (4) -> {
                if (!List.of("true", "false").contains(parsed[3].toLowerCase())) {
                    Accessory bean = new Accessory();
                    bean.setId(Long.parseLong(parsed[0]));
                    bean.setName(parsed[1]);
                    bean.setPrice(Integer.parseInt(parsed[2]));
                    bean.setDescription(parsed[3]);
                    yield bean;
                } else {
                    Camera bean = new Camera();
                    bean.setId(Long.parseLong(parsed[0]));
                    bean.setName(parsed[1]);
                    bean.setPrice(Integer.parseInt(parsed[2]));
                    bean.setIsDigital(Boolean.parseBoolean(parsed[3]));
                    yield bean;
                }
            }
            case (5) -> {
                Material bean = new Material();
                bean.setId(Long.parseLong(parsed[0]));
                bean.setName(parsed[1]);
                bean.setPrice(Integer.parseInt(parsed[2]));
                bean.setQuantity(Integer.parseInt(parsed[3]));
                bean.setUnit(parsed[4]);
                yield bean;
            }
            default -> new Product() {};
        };
    }

    public static List<Product> fromString(String string) {
        List<String> beans = List.of(string.split(beansDelimiter));
        return beans.stream().map(ProductsConverter::stringToBean).toList();
    }

    private static String beanToString(Product product) {
        List<Object> params = new ArrayList<>(List.of(product.getId(), product.getName(), product.getPrice()));

        if (product instanceof Accessory accessory) {
            params.add(accessory.getDescription());
        } else if (product instanceof Camera camera) {
            params.add(camera.getIsDigital());
        } else if (product instanceof Material material) {
            params.add(material.getQuantity());
            params.add(material.getUnit());
        }

        return params.stream().map(Object::toString).collect(Collectors.joining(fieldsDelimiter));
    }

    public static String toString(Object object) {
        List<Product> products = (List<Product>) object;
        return products.stream().map(ProductsConverter::beanToString).collect(Collectors.joining(beansDelimiter));
    }

    @Override
    public List<Product> convert(String string) {
        return fromString(string);
    }

    @Override
    public String convertToWrite(Object object) {
        return toString(object);
    }
}
