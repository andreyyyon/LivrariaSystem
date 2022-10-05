package livraria.livrariafx.model.entities;

import java.io.Serial;
import java.io.Serializable;

public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String email;

    public Person() {
    }

    public Person(Integer id, String name, String email) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
