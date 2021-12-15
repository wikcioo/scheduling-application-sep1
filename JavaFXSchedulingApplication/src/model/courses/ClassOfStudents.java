package model.courses;

import model.students.StudentList;

import java.io.Serializable;
import java.util.Objects;

/**
 * The purpose of this class is to store a Student List
 * alongside with a name in order to create a class of students
 */
public class ClassOfStudents implements Serializable
{
  private String name;
  private StudentList studentList;

  /**
   * The purpose of this constructor is to initialize values for the name
   * and the student list
   */
  public ClassOfStudents(String name, StudentList studentList)
  {
    this.name = name;
    this.studentList = studentList;
  }

  /**
   * The purpose of this constructor is to initialize the value for the name
   * and create a new StudentList object
   */
  public ClassOfStudents(String name)
  {
    this.name = name;
    this.studentList = new StudentList();
  }

  /**
   * Return a studentList object
   */
  public StudentList getStudentList()
  {
    return studentList;
  }

  /**
   * Return a String of the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * The purpose of this method is to set the name of the class
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * The purpose of this method is to compare two ClassOfStudents objects
   * and returns true if they have the same name and the same Student List,
   * otherwise false
   */
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

  /**
   * Return all the information about a ClassOfStudents object
   */
  @Override public String toString()
  {
    return "Class{" + "name='" + name + '\'' + ", studentList=" + studentList
        + '}';
  }
}
