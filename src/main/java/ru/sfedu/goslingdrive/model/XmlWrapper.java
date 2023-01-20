package ru.sfedu.goslingdrive.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;
import ru.sfedu.goslingdrive.model.bean.*;

import java.io.Serializable;
import java.util.List;

@Root(name = "List")
public class XmlWrapper<T> implements Serializable {

    @ElementListUnion({
            @ElementList(inline = true, required = false, type = AutoPart.class),
            @ElementList(inline = true, required = false, type = BodyPart.class),
            @ElementList(inline = true, required = false, type = Order.class),
            @ElementList(inline = true, required = false, type = ElectricPart.class),
            @ElementList(inline = true, required = false, type = RunningPart.class),
            @ElementList(inline = true, required = false, type = User.class),
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
