package model.students;

import java.io.Serializable;

/**
 * The purpose of this class it to store the information about a person
 */
public abstract class Person implements Serializable {
    protected String name;
    protected String email;
    protected int id;

    /**
     The purpose of this constructor is to initialize all the instance variable
     */
    public Person(String name, String email, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    /**
     * Returns the name of the person
     */
    public abstract String getName();

    /**
     * The purpose of this method is to set the name of the person
     */
    public abstract void setName(String name);

    /**
     * Returns the email of the person
     */
    public abstract String getEmail();

    /**
     * The purpose of this method is to set the email of the person
     */
    public abstract void setEmail(String email);

    /**
     * Returns the id of the person
     */
    public abstract int getId();

    /**
     * The purpose of this method is to set the id of the person
     */
    public abstract void setId(int id);
}