package model.courses;

import model.students.StudentList;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The purpose of this class is to store a title, a teacherList, a classOfStudents
 * participants and a hexColor in order to create a course
 */
public class Course implements Serializable
{
  private String title;
  private TeacherList teacherList;
  private ClassOfStudents classOfStudents;
  private StudentList participants;
  private String hexColor = "black";

  /**
   * The purpose os this constructor is to initialize the title, a class of
   * students and create a new TeacherList object
   */
  public Course(String title, ArrayList<Teacher> teacherList,
      ClassOfStudents classOfStudents)
  {
    this.title = title;
    this.teacherList = new TeacherList(teacherList);

    this.classOfStudents = classOfStudents;
    if (classOfStudents != null)
    {
      initStudents();
    }
  }

  /**
   * The purpose os this constructor is to initialize the title
   * and a teacherList and create a null object of classOfStudents
   */
  public Course(String title, ArrayList<Teacher> teacherList)
  {
    this(title, teacherList, null);
  }

  /**
   * The purpose of this method is to set the title of the course
   */
  public void setTitle(String title)
  {
    this.title = title;
  }

  /**
   * The purpose of this method is to appoint a class of students to a course
   */
  public void setClassOfStudents(ClassOfStudents classOfStudents)
  {
    this.classOfStudents = classOfStudents;
  }

  /**
   * The purpose of this method is to store in participants a copy of the
   * students of the course
   */
  public void initStudents()
  {
    try
    {
      this.participants = classOfStudents.getStudentList().copy();
    }
    catch (NullPointerException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Returns the title of the course
   */
  public String getTitle()
  {
    return title;
  }

  /**
   * Returns a teacherList object
   */
  public TeacherList getTeacherList()
  {
    return teacherList;
  }

  /**
   * The purpose of this method is to appoint new teachers to the course
   */
  public void setTeacherList(ArrayList<Teacher> teacherList)
  {
    this.teacherList = new TeacherList(teacherList);
  }

  /**
   * Returns a classOfStudents object
   */
  public ClassOfStudents getClassOfStudents()
  {
    return classOfStudents;
  }

  /**
   * Return a StudentList object
   */
  public StudentList getParticipants()
  {
    return participants;
  }

  /**
   * Returns the name of the ClassOfStudents object
   */
  public String getClassName()
  {
    return this.classOfStudents.getName();
  }

  /**
   * Return a list of all teachers of the course
   */
  public String getTeacherName()
  {
    return teacherList.toString();
  }

  /**
   * The purpose of this method is to set participants to the course
   */
  public void setParticipants(StudentList participants)
  {
    this.participants = participants;
  }

  /**
   * Return the color of the course
   */
  public String getHexColor()
  {
    return hexColor;
  }

  /**
   * The purpose of this method is to set the color of the course
   */
  public void setHexColor(String hexColor)
  {
    this.hexColor = hexColor;
  }

  /**
   * Return the title of the course
   */
  @Override public String toString()
  {
    return title;
  }
}
