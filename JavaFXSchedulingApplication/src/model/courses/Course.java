package model.courses;

import model.students.StudentList;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable
{
  private String title;
  private TeacherList teacherList;
  private Class classOfStudents;
  private StudentList participants;

  public Course(String title, Teacher teacher, Class classOfStudents)
  {
    this.title = title;
    this.teacherList = new TeacherList();
    this.teacherList.addTeacher(teacher);
    this.classOfStudents = classOfStudents;
    initStudents();
  }

  public Course(String title, Teacher teacher)
  {
    this(title, teacher, null);
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public void setClassOfStudents(Class classOfStudents)
  {
    this.classOfStudents = classOfStudents;
  }

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

  //GETTERS AND SETTERS

  public String getTitle()
  {
    return title;
  }

  public TeacherList getTeacherList()
  {
    return teacherList;
  }

  public void setTeacherList(ArrayList<Teacher> teacherList)
  {
    this.teacherList = new TeacherList(teacherList);
  }

  public Class getClassOfStudents()
  {
    return classOfStudents;
  }

  public StudentList getParticipants()
  {
    return participants;
  }

  public String getClassName(){
    return this.classOfStudents.getName();
  }
  public String getTeacherName(){
    return teacherList.toString();
  }
  public void setParticipants(StudentList participants)
  {
    this.participants = participants;
  }

  @Override public String toString()
  {
    return "Course{" + "title='" + title + '\'' + ", teacherList=" + teacherList
        + ", classOfStudents=" + classOfStudents + ", participants="
        + participants + '}';
  }
}
