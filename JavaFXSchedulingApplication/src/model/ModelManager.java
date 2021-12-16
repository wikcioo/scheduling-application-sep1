package model;

import model.calendar.*;
import model.courses.*;
import model.rooms.BookingTime;
import model.rooms.Room;
import model.rooms.RoomList;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The class is a representation of the manager that has access to all data and acts as a handler for other classes to use.
 * All the methods redirect the method call to the appropriate list objects. The exception is the InitSemester that
 * initializes the scheduleList based on the start and end time. It implements the Model interface as well as Serializable
 * interface for persistence purposes.
 */
public class ModelManager implements Model, Serializable {
    private ScheduleList scheduleList;
    private RoomList roomList;
    private CourseList courseList;
    private ClassList classList;
    private CopiedWeek copiedWeek;

    /**
     * Initializes all the lists. The schedule list is initialized based on current date.
     */
    public ModelManager() {
        this.scheduleList = new ScheduleList(LocalDate.now(), LocalDate.now().plusMonths(6));
        this.roomList = new RoomList();
        this.courseList = new CourseList();
        this.classList = new ClassList();
        this.copiedWeek = new CopiedWeek();
    }

    /**
     * Re-initializes the schedule list with the given start and end date.
     *
     * @param startTime the start time of the schedule list
     * @param endTime the end time of the schedule list
     */
    public void initSemester(LocalDate startTime, LocalDate endTime) {
        if (startTime.isBefore(endTime)) {
            this.scheduleList = new ScheduleList(startTime, endTime);
        } else
            this.scheduleList = new ScheduleList(endTime, startTime);
    }

    // scheduleList BEGIN
    public Schedule getCurrentSchedule() {
        return scheduleList.getCurrentSchedule();
    }

    public void setCurrentSchedule(Schedule currentSchedule) {
        scheduleList.setCurrentSchedule(currentSchedule);
    }

    public ScheduleList getScheduleList() {
        return scheduleList;
    }

    public ArrayList<Lesson> getAllLessons() {
        return scheduleList.getAllLessons();
    }

    public Schedule getScheduleByClass(ClassOfStudents class1, boolean createIfNoScheduleFound) {
        return scheduleList.getScheduleByClass(class1, createIfNoScheduleFound);
    }

    public Week getCurrentWeek() {
        return scheduleList.getCurrentSchedule().getCurrentWeek();
    }

    public int getCurrentWeekIndex() {
        return scheduleList.getCurrentSchedule().getCurrentWeekIndex();
    }

    public void goNextWeek() {
        scheduleList.getCurrentSchedule().goNextWeek();
    }

    public void goPreviousWeek() {
        scheduleList.getCurrentSchedule().goPreviousWeek();
    }

    public boolean hasNextWeek() {
        return scheduleList.getCurrentSchedule().hasNextWeek();
    }

    public boolean hasPreviousWeek() {
        return scheduleList.getCurrentSchedule().hasPreviousWeek();
    }

    public ArrayList<Week> getWeekList() {
        return scheduleList.getCurrentSchedule().getWeekList();
    }

    public int getNumberOfWeeksBetween() {
        return scheduleList.getCurrentSchedule().getNumberOfWeeksBetween();
    }

    public void initializeCurrentWeekIndex() {
        scheduleList.getCurrentSchedule().initializeCurrentWeekIndex();
    }

    public int getCurrentYear() {
        return scheduleList.getCurrentSchedule().getCurrentYear();
    }

    public void initializeAllWeeks() {
        scheduleList.getCurrentSchedule().initializeAllWeeks();
    }

    public void exportScheduleAsXML() {
        scheduleList.getCurrentSchedule().exportScheduleAsXML();
    }

    public void exportWeekAsXML(Week week) {
        scheduleList.getCurrentSchedule().exportWeekAsXML(week);
    }
    // scheduleList END


    // roomList BEGIN
    public ArrayList<Room> getRooms() {
        return roomList.getRooms();
    }

    public void addRoom(Room room) {
        roomList.addRoom(room);
    }

    public void removeRoom(Room room) {
        roomList.removeRoom(room);
    }

    public void setRooms(ArrayList<Room> rooms) {
        roomList.setRooms(rooms);
    }

    public ArrayList<Room> getAvailableRoomsAt(BookingTime time) {
        return roomList.getAvailableRoomsAt(time);
    }

    public ArrayList<Room> getRoomsByMinimumCapacity(int minCapacity) {
        return roomList.getRoomsByMinimumCapacity(minCapacity);
    }

    public ArrayList<Room> getMergeableRooms() {
        return roomList.getMergeableRooms();
    }

    public ArrayList<Room> getUnMergeableRooms() {
        return roomList.getUnMergeableRooms();
    }

    public ArrayList<Room> getAvailableRoomsByMinimumCapacity(int minCapacity, BookingTime time) {
        return roomList.getAvailableRoomsByMinimumCapacity(minCapacity, time);
    }

    public ArrayList<Room> getAvailableAndMergeableRooms(BookingTime time) {
        return roomList.getAvailableAndMergeableRooms(time);
    }

    public ArrayList<Room> getAvailableAndUnMergeableRooms(BookingTime time) {
        return roomList.getAvailableAndUnMergeableRooms(time);
    }

    public Room getRoomByString(String name) {
        return roomList.getRoomByString(name);
    }

    public void readRoomsListFromBinFile() {
        roomList.readRoomsListFromBinFile();
    }

    public void readRoomsFromTXTFile(File file) {
        roomList.readRoomsFromTXTFile(file);
    }
    // roomList END

    // courseList BEGIN
    public void addCourse(Course course) {
        courseList.addCourse(course);
    }

    public void removeCourse(Course course) {
        courseList.removeCourse(course);
    }

    public ArrayList<Course> getCoursesByTitle(String title) {
        return courseList.getCoursesByTitle(title);
    }

    public ArrayList<Course> getCoursesByTeachers(Teacher teacher) {
        return courseList.getCoursesByTeachers(teacher);
    }

    public ArrayList<Course> getCoursesByClass(ClassOfStudents classOfStudents) {
        return courseList.getCoursesByClass(classOfStudents);
    }

    public ArrayList<Course> getCoursesByClassName(ClassOfStudents className) {
        return courseList.getCoursesByClassName(className);
    }

    public ArrayList<Course> getCourses() {
        return courseList.getCourses();
    }

    public void readCoursesFromTXTFile(File file) {
        courseList.readCoursesFromTXTFile(file);
    }

    public void setCurrentSelectedCourse(int currentSelectedCourse) {
        courseList.setCurrentSelectedCourse(currentSelectedCourse);
    }

    public int getCurrentSelectedCourse() {
        return courseList.getCurrentSelectedCourse();
    }
    // courseList END

    // classList BEGIN
    public int getCurrentlySelectedClass() {
        return classList.getCurrentlySelectedClass();
    }

    public void setCurrentlySelectedClass(int currentlySelectedClass) {
        classList.setCurrentlySelectedClass(currentlySelectedClass);
    }

    public void addClass(ClassOfStudents _class) {
        classList.addClass(_class);
    }

    public void removeClass(ClassOfStudents _class) {
        classList.removeClass(_class);
    }

    public ArrayList<ClassOfStudents> getClasses() {
        return classList.getClasses();
    }

    public ArrayList<ClassOfStudents> copyClasses() {
        return classList.copyClasses();
    }
    // classList END

    // copiedWeek BEGIN
    public void setCopiedWeek(Week week) {
        copiedWeek.setCopiedWeek(week);
    }

    public Week getCopiedWeek() {
        return copiedWeek.getCopiedWeek();
    }

    public void removeCopiedWeek() {
        copiedWeek.removeCopiedWeek();
    }
    // copiedWeek END
}
