package ru.sfedu.trainpick.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;
import ru.sfedu.trainpick.model.bean.*;

import java.io.Serializable;
import java.util.List;

@Root(name = "List")
public class XmlWrapper<T> implements Serializable {

    @ElementListUnion({
            @ElementList(inline = true, required = false, type = ChildPassenger.class),
            @ElementList(inline = true, required = false, type = DiscountPassenger.class),
            @ElementList(inline = true, required = false, type = Passenger.class),
            @ElementList(inline = true, required = false, type = Ticket.class),
            @ElementList(inline = true, required = false, type = Train.class),
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
