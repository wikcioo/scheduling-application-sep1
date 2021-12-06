package model.courses;

import model.students.StudentList;

import java.io.Serializable;

public class Class implements Serializable
{
    private String name;
    private StudentList studentList;

    public Class(String name, StudentList studentList)
    {
        this.name = name;
        this.studentList = studentList;
    }

    public StudentList getStudentsList()
    {
        return studentList;
    }

    public StudentList getStudentList() {
        return studentList;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override public String toString()
    {
        return "Class{" + "name='" + name + '\'' + ", studentList="
            + studentList + '}';
    }
}
