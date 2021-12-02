package model;

import java.util.ArrayList;

public class Class {
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
}
