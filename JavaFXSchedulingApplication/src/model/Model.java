package model;

import model.calendar.CopiedWeek;
import model.calendar.Schedule;
import model.calendar.Week;
import model.courses.CourseList;
import model.rooms.RoomList;
import model.students.StudentList;
import model.courses.ClassList;
import model.courses.ClassOfStudents;
import model.calendar.ScheduleList;
import model.courses.CourseList;

import java.io.File;
import java.util.ArrayList;

public interface Model {
    void readStudentFromTXTFile(File file);
    Week getCurrentWeek();
    int getCurrentWeekIndex();
    void goNextWeek();
    ArrayList<ClassOfStudents> getClasses();
    boolean hasNextWeek();
    void goPreviousWeek();
    boolean hasPreviousWeek();
    ArrayList<Week> getWeekList();
    int getNumberOfWeeksBetween();
    ScheduleList getScheduleList();
    ClassList getClassList();
    void initializeCurrentWeekIndex();
    int getCurrentYear();
    void initializeAllWeeks();
    void readSemesterData();
    void saveSemesterData();
    RoomList getRoomList();
    StudentList getStudentList();
    CourseList getCourseList();
    CopiedWeek getCopiedWeekWrapper();
    void exportScheduleAsXML();
    void exportWeekAsXML(Week week);
}
