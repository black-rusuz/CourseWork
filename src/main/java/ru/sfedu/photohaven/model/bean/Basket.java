package ru.sfedu.photohaven.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import ru.sfedu.photohaven.utils.ProductsConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Basket implements Serializable {
    @Attribute
    @CsvBindByPosition(position = 0)
    private long id;

    @ElementListUnion({
            @ElementList(entry = "Camera", inline = true, required = false, type = Camera.class),
            @ElementList(entry = "Accessory", inline = true, required = false, type = Accessory.class),
            @ElementList(entry = "Material", inline = true, required = false, type = Material.class),
            @ElementList(entry = "Product", inline = true, required = false, type = Product.class),
    })
    @CsvCustomBindByPosition(position = 1, converter = ProductsConverter.class)
    private List<Product> products = new ArrayList<>();

    public Basket() {
    }

    public Basket(long id, List<Product> products) {
        setId(id);
        setProducts(products);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Basket basket)) return false;
        return getId() == basket.getId()
                && Objects.equals(getProducts(), basket.getProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProducts());
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", products=" + products +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
