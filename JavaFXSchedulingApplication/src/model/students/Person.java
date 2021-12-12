package model.students;

import java.io.Serializable;

public abstract class Person implements Serializable {
    protected String name;
    protected String email;
    protected int id;

    public Person(String name, String email, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getEmail();

    public abstract void setEmail(String email);

    public abstract int getId();

    public abstract void setId(int id);
}