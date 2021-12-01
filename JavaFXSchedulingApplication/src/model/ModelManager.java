package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class ModelManager implements Model {
    private Semester semester;

    public ModelManager() {
        this.semester = new Semester(LocalDate.of(2021, 8, 30), LocalDate.of(2021, 12, 17));
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

    public void goPreviousWeek() {
        semester.goPreviousWeek();
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
