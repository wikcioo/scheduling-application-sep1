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
   * @param title the title of the course
   * @param teacherList a list of teachers
   * @param classOfStudents a ClassOfStudents objects
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
   * @param title the title of the course
   * @param teacherList the list of teachers
   */
  public Course(String title, ArrayList<Teacher> teacherList)
  {
    this(title, teacherList, null);
  }

  /**
   * The purpose of this method is to set the title of the course
   * @param title the title of the course
   */
  public void setTitle(String title)
  {
    this.title = title;
  }

  /**
   * The purpose of this method is to appoint a class of students to a course
   * @param classOfStudents a list of students;
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
   * @return  the title of the course
   */
  public String getTitle()
  {
    return title;
  }

  /**
   * @return  a teacherList object
   */
  public TeacherList getTeacherList()
  {
    return teacherList;
  }

  /**
   * The purpose of this method is to appoint new teachers to the course
   * @param teacherList a list of the teachers
   */
  public void setTeacherList(ArrayList<Teacher> teacherList)
  {
    this.teacherList = new TeacherList(teacherList);
  }

  /**
   * @return  a classOfStudents object
   */
  public ClassOfStudents getClassOfStudents()
  {
    return classOfStudents;
  }

  /**
   * @return  a StudentList object
   */
  public StudentList getParticipants()
  {
    return participants;
  }

  /**
   * @return  the name of the ClassOfStudents object
   */
  public String getClassName()
  {
    return this.classOfStudents.getName();
  }

  /**
   * @return  a list of all teachers of the course
   */
  public String getTeacherName()
  {
    return teacherList.toString();
  }

  /**
   * The purpose of this method is to set participants to the course
   * @param participants a list of students
   */
  public void setParticipants(StudentList participants)
  {
    this.participants = participants;
  }

  /**
   * @return the color of the course
   */
  public String getHexColor()
  {
    return hexColor;
  }

  /**
   * The purpose of this method is to set the color of the course
   * @param hexColor the color of the course
   */
  public void setHexColor(String hexColor)
  {
    this.hexColor = hexColor;
  }

  /**
   * @return  the title of the course
   */
  @Override public String toString()
  {
    return title;
  }
}
