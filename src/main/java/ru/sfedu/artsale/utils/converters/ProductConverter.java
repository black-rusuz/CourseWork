package ru.sfedu.artsale.utils.converters;

import com.opencsv.bean.AbstractBeanField;
import ru.sfedu.artsale.model.bean.CreationKit;
import ru.sfedu.artsale.model.bean.EndProduct;
import ru.sfedu.artsale.model.bean.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductConverter extends AbstractBeanField<Product, String> {
    public static final String fieldsDelimiter = "::";

    @Override
    public Product convert(String s) {
        String[] parsed = s.split(fieldsDelimiter);
        return switch (parsed.length) {
            case (3) -> new Product(Long.parseLong(parsed[0]), parsed[1], Double.parseDouble(parsed[2]));
            case (4) -> new EndProduct(Long.parseLong(parsed[0]), parsed[1], Double.parseDouble(parsed[2]), Integer.parseInt(parsed[3]));
            case (5) -> new CreationKit(Long.parseLong(parsed[0]), parsed[1], Double.parseDouble(parsed[2]), parsed[3], parsed[4]);
            default -> new Product();
        };
    }

    @Override
    public String convertToWrite(Object object) {
        List<Object> params;
        if (object instanceof CreationKit creationKit) {
            params = List.of(creationKit.getId(),
                    creationKit.getName(),
                    creationKit.getPrice(),
                    creationKit.getCategory(),
                    creationKit.getComposition());
        }
        else if (object instanceof EndProduct endProduct) {
            params = List.of(endProduct.getId(),
                    endProduct.getName(),
                    endProduct.getPrice(),
                    endProduct.getWarranty());
        }
        else {
            Product product = (Product) object;
            params = List.of(product.getId(),
                    product.getName(),
                    product.getPrice());
        }
        return params.stream().map(Object::toString).collect(Collectors.joining(fieldsDelimiter));
    }
}
