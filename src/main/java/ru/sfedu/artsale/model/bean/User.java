package ru.sfedu.artsale.model.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import ru.sfedu.artsale.utils.JdbcUtil;

import java.io.Serializable;
import java.util.Objects;

@Element
public class User implements Serializable {

    @Attribute
    @CsvBindByPosition(position = 0)
    private long id;

    @Element
    @CsvBindByPosition(position = 1)
    private String name = "";

    @Element
    @CsvBindByPosition(position = 2)
    private String phone = "";

    @Element
    @CsvBindByPosition(position = 3)
    private String email = "";

    @Element
    @CsvBindByPosition(position = 4)
    private String address = "";

    public User() {
    }

    public User(long id, String name, String phone, String email, String address) {
        setId(id);
        setName(name);
        setPhone(phone);
        setEmail(email);
        setAddress(address);
    }

    public static String toCreateTableString() {
        return String.format(
                "CREATE TABLE IF NOT EXISTS %suser (id LONG PRIMARY KEY, name VARCHAR, phone VARCHAR, email VARCHAR, address VARCHAR);",
                JdbcUtil.tablePrefix);
    }

    public String toInsertString() {
        return "'" + getId() + "', '"
                + getName() + "', '"
                + getPhone() + "', '"
                + getEmail() + "', '"
                + getAddress() + "'";
    }

    public String toUpdateString() {
        return "id = '" + getId() + "', "
                + "name = '" + getName() + "', "
                + "phone = '" + getPhone() + "', "
                + "email = '" + getEmail() + "', "
                + "address = '" + getAddress() + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getId() == user.getId()
                && Objects.equals(getName(), user.getName())
                && Objects.equals(getPhone(), user.getPhone())
                && Objects.equals(getEmail(), user.getEmail())
                && Objects.equals(getAddress(), user.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPhone(), getEmail(), getAddress());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", address='" + getAddress() + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
