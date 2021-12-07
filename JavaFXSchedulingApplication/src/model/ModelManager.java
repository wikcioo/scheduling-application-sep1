package model;

import controller.CourseListViewController;
import model.calendar.Schedule;
import model.calendar.Week;
import model.courses.ClassList;
import model.courses.CourseList;
import model.rooms.RoomList;
import model.students.Student;
import model.students.StudentList;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class ModelManager implements Model {
    private Schedule schedule;
    private StudentList studentList;
    private RoomList roomList;
    private CourseList courseList;
    private ClassList classList;

    public ModelManager() {
        this.schedule = new Schedule(LocalDate.of(2021, 8, 30), LocalDate.of(2021, 12, 17));
        this.studentList = new StudentList();
        this.roomList = new RoomList();
        this.courseList = new CourseList();
        this.classList = new ClassList();
        studentList.readStudentListFromBinFile();
        readSemesterData();
    }
    public StudentList getStudentList(){
        return this.studentList;
    }

    public CourseList getCourseList(){
        return this.courseList;
    }
    public RoomList getRoomList(){
        return this.roomList;
    }
    public void readStudentFromTXTFile(File file) {
        studentList.readStudentFromTXTFile(file);
    }

    public void setSemester(LocalDate semesterStart, LocalDate semesterEnd) {
        this.schedule = new Schedule(semesterStart, semesterEnd);
    }

    public Week getCurrentWeek() {
        return schedule.getCurrentWeek();
    }

    public int getCurrentWeekIndex() {
        return schedule.getCurrentWeekIndex();
    }

    public void goNextWeek() {
        schedule.goNextWeek();
    }

    @Override
    public boolean hasNextWeek() {
        return schedule.hasNextWeek();
    }

    public void goPreviousWeek() {
        schedule.goPreviousWeek();
    }

    @Override
    public boolean hasPreviousWeek() {
        return schedule.hasPreviousWeek();
    }

    public ArrayList<Week> getWeekList() {
        return schedule.getWeekList();
    }

    public int getNumberOfWeeksBetween() {
        return schedule.getNumberOfWeeksBetween();
    }

    public void initializeCurrentWeekIndex() {
        schedule.initializeCurrentWeekIndex();
    }

    public int getCurrentYear() {
        return schedule.getCurrentYear();
    }

    public void initializeAllWeeks() {
        schedule.initializeAllWeeks();
    }

    public void readSemesterData() {
        schedule.readData();
    }

    public void saveSemesterData() {
        schedule.saveData();
    }
}
