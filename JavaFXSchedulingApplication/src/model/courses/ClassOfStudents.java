package model.courses;

import model.students.StudentList;

import java.io.Serializable;
import java.util.Objects;

public class ClassOfStudents implements Serializable
{
    private String name;
    private StudentList studentList;

    public ClassOfStudents(String name, StudentList studentList)
    {
        this.name = name;
        this.studentList = studentList;
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

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ClassOfStudents that = (ClassOfStudents) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(
            getStudentList(), that.getStudentList());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getName(), getStudentList());
    }

    @Override public String toString()
    {
        return "Class{" + "name='" + name + '\'' + ", studentList="
            + studentList + '}';
    }
}
