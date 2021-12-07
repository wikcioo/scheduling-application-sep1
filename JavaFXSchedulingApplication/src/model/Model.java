package model;

import model.calendar.Week;
import model.courses.CourseList;
import model.rooms.RoomList;
import model.students.StudentList;

import java.io.File;
import java.util.ArrayList;

public interface Model {
    void readStudentFromTXTFile(File file);
    Week getCurrentWeek();
    int getCurrentWeekIndex();
    void goNextWeek();
    boolean hasNextWeek();
    void goPreviousWeek();
    boolean hasPreviousWeek();
    ArrayList<Week> getWeekList();
    int getNumberOfWeeksBetween();
    void initializeCurrentWeekIndex();
    int getCurrentYear();
    void initializeAllWeeks();
    void readSemesterData();
    void saveSemesterData();
    RoomList getRoomList();
    StudentList getStudentList();
    CourseList getCourseList();
}
