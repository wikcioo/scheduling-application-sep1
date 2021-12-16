package model.courses;

import utilities.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * The purpose of this class is to store an ArrayList of Course objects in order
 * to manage them
 */
public class CourseList implements Serializable
{
  private ArrayList<Course> courses;
  private int currentSelectedCourse;

  /**
   * The purpose of this constructor is to initialize the courses ArrayList
   */
  public CourseList()
  {
    this.courses = new ArrayList<>();
  }

  /**
   * The purpose of this method is to add a course to the courses ArrayList
   * @param course a list of all courses
   */
  public void addCourse(Course course)
  {
    courses.add(course);
  }

  /**
   * The purpose of this method is to remove a course from the courses ArrayList
   * @param course a list of all courses
   */
  public void removeCourse(Course course)
  {
    courses.remove(course);
  }

  /**
   * The purpose of this method is to return a list of courses with a specific name
   * @param  title the title of the course
   * @return all the courses with that title
   */
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

  /**
   * The purpose of this method is to return a list of courses with a specific teacher
   * @param teacher a teacher object
   * @return  a list of courses where that teacher belongs to
   */
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

  /**
   * The purpose of this method is to return a list of courses which belong to a specific class of students
   * @param classOfStudents a class of students
   * @return a list of courses which have a specific class of students
   */
  public ArrayList<Course> getCoursesByClass(ClassOfStudents classOfStudents)
  {
    ArrayList<Course> result = new ArrayList<>();
    for (Course course : courses)
    {
      if (Objects.equals(classOfStudents, course.getClassOfStudents()))
        result.add(course);
    }
    return result;
  }

  /**
   * The purpose of this method is to return a list of courses which belongs to a specific class
   * @param className  the name of the class
   * @return a list of courses which belongs to a specific class
   */
  public ArrayList<Course> getCoursesByClassName(ClassOfStudents className)
  {
    ArrayList<Course> result = new ArrayList<>();
    for (Course course : courses)
    {
      if (Objects.equals(className, course.getClassOfStudents().getName()))
        result.add(course);
    }
    return result;
  }

  /**
   * @return  list of all the courses
   */
  public ArrayList<Course> getCourses()
  {
    return courses;
  }

  /**
   * The purpose of this method is to read all the course information from a
   * txt file
   * @param file the file we read from, all the courses data
   */
  public void readCoursesFromTXTFile(File file)
  {

    Scanner in = null;
    try
    {
      in = new Scanner(file);
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    while (in.hasNext())
    {
      int semester = -1;
      String _class = null;
      String courseTitle = null;
      String teacher = null;
      int ects = -1;
      String line = in.nextLine();
      String[] token = line.split(",");
      try
      {
        semester = Integer.parseInt(token[0]);
        _class = token[1];
        courseTitle = token[2];
        teacher = token[3];
        ects = Integer.parseInt(token[4]);

      }
      catch (NumberFormatException e)
      {
        Logger.error("Wrong format, skipping...");
        e.printStackTrace();
      }
      ArrayList<Teacher> teacherList = new ArrayList<>();
      teacherList.add(new Teacher(teacher));
      courses.add(
          new Course(courseTitle, teacherList, new ClassOfStudents("1Z")));
    }

    ArrayList<Teacher> teach = new ArrayList<>();
    teach.add(new Teacher("t1"));
    teach.add(new Teacher("t2"));
    teach.add(new Teacher("t3"));
    courses.get(0).setTeacherList(teach);
    in.close();
  }

  /**
   * The purpose of this method is set the int value of currentlySelectedClass
   * when a class is clicked/selected
   * @param currentSelectedCourse the selected course;
   */
  public void setCurrentSelectedCourse(int currentSelectedCourse)
  {

    this.currentSelectedCourse = currentSelectedCourse;
  }

  /**
   * @return  the int value of currentSelectedCourse
   */
  public int getCurrentSelectedCourse()
  {
    return currentSelectedCourse;
  }
}
