package ru.sfedu.photohaven.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;
import ru.sfedu.photohaven.model.bean.*;

import java.io.Serializable;
import java.util.List;

@Root(name = "List")
public class XmlWrapper<T> implements Serializable {

    @ElementListUnion({
            @ElementList(inline = true, required = false, type = Accessory.class),
            @ElementList(inline = true, required = false, type = Basket.class),
            @ElementList(inline = true, required = false, type = Camera.class),
            @ElementList(inline = true, required = false, type = Material.class),
            @ElementList(inline = true, required = false, type = Product.class),
    })
    private List<T> list;

    public XmlWrapper() {
    }

    public XmlWrapper(List<T> list) {
        setList(list);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
