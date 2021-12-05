package model.courses;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class CourseList
{
  private ArrayList<Course> courses;

  public CourseList()
  {
    this.courses = new ArrayList<>();
  }

  public void addCourse(Course course)
  {
    courses.add(course);
  }

  public void removeCourse(Course course)
  {
    courses.remove(course);
  }

  public ArrayList<Course> getCoursesByTitle(String title)
  {
    ArrayList<Course> result = new ArrayList<>();
    for (Course course : courses)
    {
      if (Objects.equals(title, course.getTitle()))
        result.add(course);
    }
    return result;
  }

  public ArrayList<Course> getCoursesByTeachers(Teacher teacher)
  {
    ArrayList<Course> result = new ArrayList<>();
    for (Course course : courses)
    {
      for (int i = 0; i < course.getTeacherList().size(); i++)
        if (teacher.equals(course.getTeacherList().getTeacherByIndex(i)))
          result.add(course);
    }
    return result;
  }

  public ArrayList<Course> getCoursesByClass(Class classOfStudents)
  {
    ArrayList<Course> result = new ArrayList<>();
    for (Course course : courses)
    {
      if (Objects.equals(classOfStudents, course.getClassOfStudents()))
        result.add(course);
    }
    return result;
  }

  public ArrayList<Course> getCoursesByClassName(Class className)
  {
    ArrayList<Course> result = new ArrayList<>();
    for (Course course : courses)
    {
      if (Objects.equals(className, course.getClassOfStudents().getName()))
        result.add(course);
    }
    return result;
  }

  public ArrayList<Course> getCourses()
  {
    return courses;
  }

  public void importCourses()
  {

  }

  public void readCourseListFromBinFile() {
    String filename = "res/saved-data/courseList.bin";
    ObjectInputStream in = null;
    try {
      File file = new File(filename);
      FileInputStream fis = new FileInputStream(file);
      in = new ObjectInputStream(fis);
      Course course;
      while((course = (Course) in.readObject()) != null){
        this.courses.add(course);
        System.out.println(course);
      }

    } catch (IOException | ClassNotFoundException e) {
      //e.printStackTrace();
    } finally {
      try {
        in.close();
      } catch (IOException e) {
        //e.printStackTrace();
      }
    }
  }

  public void writeCourseListToBinFile() {
    String filename = "res/saved-data/courseList.bin";

    ObjectOutputStream out = null;
    try {
      File file = new File(filename);
      FileOutputStream fos = new FileOutputStream(file);
      out = new ObjectOutputStream(fos);
      for(Course course : this.courses){
        out.writeObject(course);
      }
    } catch (IOException e) {
      System.out.println("Exception: " + filename);
      e.printStackTrace();
    } finally {
      try {
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
