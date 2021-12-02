package model;

import java.util.ArrayList;
import java.util.Objects;

public class Course
{
  private String title;
  private ArrayList<Teacher> teachers;
  private Class classOfStudents;
  private StudentList participants;

  public Course(String title, Teacher teacher, Class classOfStudents)
  {
    this.title = title;
    this.teachers = new ArrayList<>();
    this.teachers.add(teacher);
    this.participants = null;
    this.classOfStudents = classOfStudents;
  }

  public Course(String title, Teacher teacher)
  {
    this(title, teacher, null);
  }



  public void setTitle(String title)
  {
    this.title = title;
  }

  public void addTeacher(Teacher teacher)
  {
    this.teachers.add(teacher);
  }

  public void removeTeacher(Teacher teacher)
  {
      teachers.remove(teacher);
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

  public ArrayList<Teacher> getTeachers()
  {
    return teachers;
  }

  public void setTeachers(ArrayList<Teacher> teachers)
  {
    this.teachers = teachers;
  }

  public Class getClassOfStudents()
  {
    return classOfStudents;
  }

  public StudentList getParticipants()
  {
    return participants;
  }

  public void setParticipants(StudentList participants)
  {
    this.participants = participants;
  }
}
