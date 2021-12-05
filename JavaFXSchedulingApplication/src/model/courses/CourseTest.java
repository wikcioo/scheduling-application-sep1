package model.courses;

import model.courses.Class;
import model.courses.Course;
import model.courses.CourseList;
import model.courses.Teacher;
import model.students.Student;
import model.students.StudentList;

public class CourseTest
{
  public static void main(String[] args)
  {
    StudentList students = new StudentList();
    Student student1 = new Student("Polo",2,"x",3);
    Student student2 = new Student("Polo2",3,"x",3);
    students.addStudent(student1);
    students.addStudent(student2);
    Class classofStudents = new Class("X",students);
    Teacher teacher = new Teacher();
    Course course = new Course("sdj",teacher,classofStudents);
    course.getParticipants().removeStudent(student1);
//    System.out.println(course.getClassOfStudents());
//    System.out.println(course.getParticipants());

    CourseList courses = new CourseList();
    for(int i = 0; i < 10 ; i++) {
      courses.addCourse(course);
    }
    courses.readCourseListFromBinFile();




  }
}
