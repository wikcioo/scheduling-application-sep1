package model.courses;

import java.io.Serializable;

public class Teacher implements Serializable
{
    private String name;

    public Teacher(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
