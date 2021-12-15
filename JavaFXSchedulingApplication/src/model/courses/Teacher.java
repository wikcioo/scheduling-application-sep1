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
     * @param name the name of the teacher
     */
    public Teacher(String name) {
        super(name, "", 0);
    }

    /**
     * @return the name of the teacher
     */
    public String getName() {
        return name;
    }

    /**
     * The purpose of this method is to set the teacher's name
     * @param name the name of the teacher
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return  the teacher's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * The purpose of this method is to set the teacher's email
     * @param email the email of the teacher
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return  the teacher's id
     */
    public int getId() {
        return id;
    }

    /**
     * The purpose of this method is to set the teacher's id
     * @param id the id of the teacher
     */
    public void setId(int id) {
        this.id = id;
    }
}