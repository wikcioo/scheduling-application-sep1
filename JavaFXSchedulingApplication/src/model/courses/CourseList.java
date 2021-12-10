package model.courses;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class CourseList implements Serializable
{
  private ArrayList<Course> courses;
  private int currentSelectedCourse;

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

  public ArrayList<Course> getCourses()
  {
    return courses;
  }

  public void importCourses()
  {

  }

  public void readCourseListFromBinFile() {
    String filename = "res/saved-data/saved-lists/courseList.bin";
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
      e.printStackTrace();
    } finally {
      try {
        in.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public  void readCoursesFromTXTFile(File file) {

    Scanner in = null;
    try {
      in = new Scanner(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    while (in.hasNext()){
      int semester = -1;
      String _class = null;
      String courseTitle = null;
      String teacher = null;
      int ects=-1;
      String line = in.nextLine();
      String[] token = line.split(",");
      try {
        semester = Integer.parseInt(token[0]);
        _class = token[1];
        courseTitle = token[2];
        teacher = token[3];
        ects = Integer.parseInt(token[4]);


      } catch (NumberFormatException e) {
        System.out.println("Wrong format, skipping...");
        e.printStackTrace();
      }
      ArrayList<Teacher> teacherList = new ArrayList<>();
      teacherList.add(new Teacher(teacher));
      courses.add(new Course(courseTitle, teacherList, new ClassOfStudents("1Z")));


    }
    ArrayList<Teacher> teach = new ArrayList<>();
    teach.add(new Teacher("fuck"));
    teach.add(new Teacher("this"));
    teach.add(new Teacher("shit"));
    courses.get(0).setTeacherList(teach);
    in.close();
  }
  public void writeCourseListToBinFile(String _file) {
    String filename;
    filename = Objects.requireNonNullElse(_file, "res/saved-data/saved-lists/courseList.bin");

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

  public void setCurrentSelectedCourse(int currentSelectedCourse) {
    this.currentSelectedCourse = currentSelectedCourse;
  }

  public int getCurrentSelectedCourse() {
    return currentSelectedCourse;
  }
}
