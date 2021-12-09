package model;

import model.calendar.CopiedWeek;
import model.calendar.ScheduleList;
import model.calendar.Week;
import model.courses.ClassList;
import model.courses.ClassOfStudents;
import model.courses.CourseList;
import model.courses.TeacherList;
import model.rooms.RoomList;
import model.students.StudentList;

import java.io.File;
import java.util.ArrayList;

public class ModelManager implements Model {
    private ScheduleList scheduleList;
    private StudentList studentList;
    private RoomList roomList;
    private CourseList courseList;
    private ClassList classList;
    private TeacherList teacherList;
    private CopiedWeek copiedWeek;

    public ModelManager() {
        this.scheduleList = new ScheduleList();
        this.studentList = new StudentList();
        this.roomList = new RoomList();
        this.courseList = new CourseList();
        this.classList = new ClassList();
        this.teacherList = new TeacherList();
        this.copiedWeek = new CopiedWeek();
        studentList.readStudentListFromBinFile();
        //readSemesterData();
    }

    public CopiedWeek getCopiedWeekWrapper() {
        return copiedWeek;
    }

    public ScheduleList getScheduleList() {
        return scheduleList;
    }

    public ClassList getClassList() {
        return classList;
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


    public Week getCurrentWeek() {
        return scheduleList.getCurrentSchedule().getCurrentWeek();
    }

    public int getCurrentWeekIndex() {
        return scheduleList.getCurrentSchedule().getCurrentWeekIndex();
    }

    public void goNextWeek() {
        scheduleList.getCurrentSchedule().goNextWeek();
    }

    @Override
    public boolean hasNextWeek() {
        return scheduleList.getCurrentSchedule().hasNextWeek();
    }

    public void goPreviousWeek() {
        scheduleList.getCurrentSchedule().goPreviousWeek();
    }

    @Override
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

    public ArrayList<ClassOfStudents> getClasses() { return classList.getClasses(); }

    public int getCurrentYear() {
        return scheduleList.getCurrentSchedule().getCurrentYear();
    }

    public void initializeAllWeeks() {
        scheduleList.getCurrentSchedule().initializeAllWeeks();
    }

    public void readSemesterData() {
        scheduleList.getCurrentSchedule().readData();
    }

    public void saveSemesterData() {
        scheduleList.getCurrentSchedule().saveData();
    }

    public void exportScheduleAsXML() {
        scheduleList.getCurrentSchedule().exportScheduleAsXML();
    }

    public void exportWeekAsXML(Week week) {
        scheduleList.getCurrentSchedule().exportWeekAsXML(week);
    }

    public void saveModels() {
        String system_state_path = "res/saved-data/system_state.bin";
        scheduleList.writeScheduleListToBinFile(system_state_path);
        studentList.writeStudentListToBinFile(system_state_path);
        roomList.writeRoomListToBinFile(system_state_path);
        courseList.writeCourseListToBinFile(system_state_path);
        // TODO: implement methods below;
        // classList.writeClassListToBinFile(system_state_path);
        // teacherList.writeTeacherListToBinFile(system_state_path);
    }
}
