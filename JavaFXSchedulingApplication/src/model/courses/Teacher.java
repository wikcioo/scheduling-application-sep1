package model.courses;

import model.students.Person;

import java.io.Serializable;

/**
 * The purpose of this class is to store information about a teacher
 * The class extends the Person class which means whatever a Person object has,
 * a teacher will have it too
 */
public class Teacher extends Person implements Serializable {

    /**
     * The purpose of the constructor is to initialize all the instance variables
     * that a teacher has in order to create a teacher object
     */
    public Teacher(String name) {
        super(name, "", 0);
    }

    /**
     * Return the name of the teacher
     */
    public String getName() {
        return name;
    }

    /**
     * The purpose of this method is to set the teacher's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the teacher's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * The purpose of this method is to set the teacher's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the teacher's id
     */
    public int getId() {
        return id;
    }

    /**
     * The purpose of this method is to set the teacher's id
     */
    public void setId(int id) {
        this.id = id;
    }
}