package model;

import model.calendar.*;
import model.courses.*;
import model.rooms.BookingTime;
import model.rooms.Room;
import model.students.Student;
import model.students.StudentList;

import java.io.File;
import java.util.ArrayList;

public interface Model {
    // scheduleList BEGIN
    Schedule getCurrentSchedule();

    void setCurrentSchedule(Schedule currentSchedule);

    void addSchedule(Schedule schedule);

    ArrayList<Lesson> getAllLessons();

    Schedule getScheduleByClass(ClassOfStudents class1, boolean createIfNoScheduleFound);

    Week getCurrentWeek();

    int getCurrentWeekIndex();

    void goNextWeek();

    void goPreviousWeek();

    boolean hasNextWeek();

    boolean hasPreviousWeek();

    ArrayList<Week> getWeekList();

    int getNumberOfWeeksBetween();

    void initializeCurrentWeekIndex();

    int getCurrentYear();

    void initializeAllWeeks();

    void exportScheduleAsXML();

    void exportWeekAsXML(Week week);
    // scheduleList END

    // studentList BEGIN
    StudentList copy();

    void setStudentList(ArrayList<Student> studentList);

    ArrayList<Student> getStudentList();

    Student getStudent(int index);

    void addStudent(Student student);

    void removeStudent(Student student);

    void removeStudent(int index);

    ArrayList<Student> getStudentsByClass(String _class);

    ArrayList<Student> getStudentsByName(String name);

    void readStudentFromTXTFile(File file);
    // studentList END

    // roomList BEGIN
    ArrayList<Room> getRooms();

    void addRoom(Room room);

    void removeRoom(Room room);

    void setRooms(ArrayList<Room> rooms);

    ArrayList<Room> getAvailableRoomsAt(BookingTime time);

    ArrayList<Room> getRoomsByMinimumCapacity(int minCapacity);

    ArrayList<Room> getMergeableRooms();

    ArrayList<Room> getUnMergeableRooms();

    ArrayList<Room> getAvailableRoomsByMinimumCapacity(int minCapacity, BookingTime time);

    ArrayList<Room> getAvailableAndMergeableRooms(BookingTime time);

    ArrayList<Room> getAvailableAndUnMergeableRooms(BookingTime time);

    Room getRoomByString(String name);

    void readRoomsListFromBinFile();

    void readRoomsFromTXTFile(File file);
    // roomList END

    // courseList BEGIN
    void addCourse(Course course);

    void removeCourse(Course course);

    ArrayList<Course> getCoursesByTitle(String title);

    ArrayList<Course> getCoursesByTeachers(Teacher teacher);

    ArrayList<Course> getCoursesByClass(ClassOfStudents classOfStudents);

    ArrayList<Course> getCoursesByClassName(ClassOfStudents className);

    ArrayList<Course> getCourses();

    void readCoursesFromTXTFile(File file);

    void setCurrentSelectedCourse(int currentSelectedCourse);

    int getCurrentSelectedCourse();
    // courseList END

    // classList BEGIN
    int getCurrentlySelectedClass();

    void setCurrentlySelectedClass(int currentlySelectedClass);

    void addClass(ClassOfStudents _class);

    void removeClass(ClassOfStudents _class);

    ArrayList<ClassOfStudents> getClasses();

    ArrayList<ClassOfStudents> copyClasses();
    // classList END

    // copiedWeek BEGIN
    void setCopiedWeek(Week week);

    Week getCopiedWeek();

    void removeCopiedWeek();
    // copiedWeek END
}
