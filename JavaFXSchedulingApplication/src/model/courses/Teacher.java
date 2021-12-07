package model.courses;

import model.students.Person;

import java.io.Serializable;

public class Teacher extends Person implements Serializable {
    public Teacher(String name) {
        super(name, "", 0);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}