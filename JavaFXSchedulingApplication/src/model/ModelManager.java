package model;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class ModelManager implements Model {
    private Semester semester;
    private StudentList studentList;

    public ModelManager() {
        this.semester = new Semester(LocalDate.of(2021, 8, 30), LocalDate.of(2021, 12, 17));
        this.studentList = new StudentList();
        studentList.readStudentListFromBinFile();
    }

    public void readStudentFromTXTFile(File file) {
        studentList.readStudentFromTXTFile(file);
    }

    public void setSemester(LocalDate semesterStart, LocalDate semesterEnd) {
        this.semester = new Semester(semesterStart, semesterEnd);
    }

    public Week getCurrentWeek() {
        return semester.getCurrentWeek();
    }

    public int getCurrentWeekIndex() {
        return semester.getCurrentWeekIndex();
    }

    public void goNextWeek() {
        semester.goNextWeek();
    }

    @Override
    public boolean hasNextWeek() {
        return semester.hasNextWeek();
    }

    public void goPreviousWeek() {
        semester.goPreviousWeek();
    }

    @Override
    public boolean hasPreviousWeek() {
        return semester.hasPreviousWeek();
    }

    public ArrayList<Week> getWeekList() {
        return semester.getWeekList();
    }

    public int getNumberOfWeeksBetween() {
        return semester.getNumberOfWeeksBetween();
    }

    public void initializeCurrentWeekIndex() {
        semester.initializeCurrentWeekIndex();
    }

    public int getCurrentYear() {
        return semester.getCurrentYear();
    }

    public void initializeAllWeeks() {
        semester.initializeAllWeeks();
    }
}
