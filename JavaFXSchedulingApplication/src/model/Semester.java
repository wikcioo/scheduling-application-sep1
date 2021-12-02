package model;

import utilities.Util;

import java.time.LocalDate;
import java.util.ArrayList;

public class Semester {
    private final ArrayList<Week> weekList;
    private final int numberOfWeeksInSemester;
    private int currentWeekIndex;
    private final LocalDate semesterStart;
    private final LocalDate semesterEnd;

    public Semester(LocalDate semesterStart, LocalDate semesterEnd) {
        this.weekList = new ArrayList<>();
        this.semesterStart = semesterStart;
        this.semesterEnd = semesterEnd;
        this.numberOfWeeksInSemester = getNumberOfWeeksBetween();
        initializeAllWeeks();
        initializeCurrentWeekIndex();
    }

    public Week getCurrentWeek() {
        return weekList.get(currentWeekIndex);
    }

    public int getCurrentWeekIndex() {
        return currentWeekIndex;
    }

    public void goNextWeek() {
        currentWeekIndex++;
    }

    public void goPreviousWeek() {
        currentWeekIndex--;
    }

    public ArrayList<Week> getWeekList() {
        return this.weekList;
    }

    public int getNumberOfWeeksBetween() {
        int i = 0;
        while (semesterStart.plusWeeks(i).compareTo(semesterEnd) < 0) {
            i++;
        }

        return i;
    }

    public void initializeCurrentWeekIndex() {
        LocalDate currentWeekMonday = Util.getMonday();
        for (int i = 0; i < weekList.size(); i++) {
            if (weekList.get(i).getStart().equals(currentWeekMonday)) {
                currentWeekIndex = i;
                break;
            }
        }
    }

    public int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    public void initializeAllWeeks() {
        for (int i = 0; i < this.numberOfWeeksInSemester; i++) {
            weekList.add(new Week(semesterStart.plusWeeks(i), semesterStart.plusWeeks(i).plusDays(6)));
        }
    }

    @Override
    public String toString() {
        return "Semester{" +
                "weekList=" + weekList +
                ", numberOfWeeksInSemester=" + numberOfWeeksInSemester +
                ", currentWeekIndex=" + currentWeekIndex +
                ", semesterStart=" + semesterStart +
                ", semesterEnd=" + semesterEnd +
                '}';
    }
}
