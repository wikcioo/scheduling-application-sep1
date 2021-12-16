package model.calendar;

import model.courses.ClassOfStudents;
import utilities.Util;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class is a representation of the schedule that holds data about all the weeks in a given schedule, class of
 * students that belongs to the schedule, the number of weeks in a semester, currently processed week index,
 * the start and end date of the semester. It implements Serializable interface for persistence purposes.
 */
public class Schedule implements Serializable {
    private final ArrayList<Week> weekList;
    private ClassOfStudents classOfStudents;
    private final int numberOfWeeksInSemester;
    private int currentWeekIndex;
    private final LocalDate semesterStart;
    private final LocalDate semesterEnd;

    /**
     * Initializes the class fields with given parameters. Then calculates the number of weeks in a semester and
     * initializes all weeks and the week index.
     *
     * @param semesterStart the starting date of the semester
     * @param semesterEnd the ending date of the semester
     * @param classOfStudents the class of students
     */
    public Schedule(LocalDate semesterStart, LocalDate semesterEnd, ClassOfStudents classOfStudents) {
        this.weekList = new ArrayList<>();
        this.classOfStudents = classOfStudents;
        this.semesterStart = semesterStart;
        this.semesterEnd = semesterEnd;
        this.numberOfWeeksInSemester = getNumberOfWeeksBetween();
        initializeAllWeeks();
        initializeCurrentWeekIndex();
    }

    /**
     * @return the class of students
     */
    public ClassOfStudents getClassOfStudents() {
        return classOfStudents;
    }

    /**
     * @return true if there is a valid next week
     */
    public boolean hasNextWeek() {
        return weekList.size() - 1 > currentWeekIndex;
    }

    /**
     * @return true if there is a valid previous week
     */
    public boolean hasPreviousWeek() {
        return currentWeekIndex > 0;
    }

    /**
     * @return the week at current week index
     */
    public Week getCurrentWeek() {
        return weekList.get(currentWeekIndex);
    }

    /**
     * @return the week index itself
     */
    public int getCurrentWeekIndex() {
        return currentWeekIndex;
    }

    /**
     * Increments the current week index by one.
     */
    public void goNextWeek() {
        currentWeekIndex++;
    }

    /**
     * Decrements the current week index by one.
     */
    public void goPreviousWeek() {
        currentWeekIndex--;
    }

    /**
     * @return the array list of weeks
     */
    public ArrayList<Week> getWeekList() {
        return this.weekList;
    }

    /**
     * @return the number of weeks between the starting and ending date of the semester
     */
    public int getNumberOfWeeksBetween() {
        int i = 0;
        while (semesterStart.plusWeeks(i).compareTo(semesterEnd) < 0) {
            i++;
        }

        return i;
    }

    /**
     * Initializes the current week index based on the starting date of a semester.
     */
    public void initializeCurrentWeekIndex() {
        LocalDate currentWeekMonday = Util.getMonday();
        for (int i = 0; i < weekList.size(); i++) {
            if (weekList.get(i).getStart().equals(currentWeekMonday)) {
                currentWeekIndex = i;
                break;
            }
        }
    }

    /**
     * @return the current year
     */
    public int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    /**
     *  This method initializes all weeks in a semester.
     */
    public void initializeAllWeeks() {
        // Iterate through all weeks in a semester
        for (int i = 0; i < this.numberOfWeeksInSemester; i++) { // This loop will run n times
            // Create a new week and initialize it with the appropriate start and end date
            Week week = new Week(semesterStart.plusWeeks(i), semesterStart.plusWeeks(i).plusDays(6)); // This takes 61 + 2 time units
            weekList.add(week); // Time takes constant time (1) in average case and linear time (n) in worst case
        }

        // We have no recursion, so we do not need a base case
        //
        // T(n) = n * (61 + 2 + 1)
        // T(n) = O(n)   - best case scenario
        //
        // T(n) = n * (61 + 2 + n)
        // T(n) = O(n^2) - worst case scenario
    }

    /**
     * @return the semester start date
     */
    public LocalDate getSemesterStart() {
        return semesterStart;
    }

    /**
     * Sets a new current week index after first validating it.
     *
     * @param currentWeekIndex the new week index to be set
     */
    public void setCurrentWeekIndex(int currentWeekIndex) {
        if (currentWeekIndex > 0 && currentWeekIndex < this.weekList.size()) {
            this.currentWeekIndex = currentWeekIndex;
        }
    }

    /**
     * Exports the entire schedule data in form of xml file. Saves newly created file in res/saved-data/schedule.xml.
     */
    public void exportScheduleAsXML() {
        File file = new File("res/saved-data/schedule.xml");
        PrintWriter out = null;
        try {
            out = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String xml = "";
        xml += "<schedule>";
        for (Week week : this.weekList) {
            xml += "\n" + weekToXML(week);
        }
        xml += "\n</schedule>";

        assert (out != null);
        out.println(xml);
        out.close();
    }

    /**
     * Exports a single week's data in form of xml file. Saves newly created file in res/saved-data/week-schedule.xml.
     *
     * @param week the week to be exported as xml
     */
    public void exportWeekAsXML(Week week) {
        File file = new File("res/saved-data/week-schedule.xml");
        PrintWriter out = null;
        try {
            out = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String xml = "";
        xml += "<schedule>";
        xml += weekToXML(week);
        xml += "\n</schedule>";

        assert (out != null);
        out.println(xml);
        out.close();
    }

    /**
     * Exports a single week's data as xml format.
     *
     * @param week the week to be converted to xml
     * @return xml format of the week
     */
    private String weekToXML(Week week) {
        String xml = "";
        xml += "<week>";
        for (Day day : week.getDays()) {
            xml += "\n\t<day date=\"" + day.getDate() + "\">";
            for (Lesson lesson : day.getLessons()) {
                xml += "\n\t\t<lesson colour=\""+lesson.getCourse().getHexColor()+"\">";
                xml += "\n\t\t\t<course>" + lesson.getCourse() + "</course>";
                xml += "\n\t\t\t<startTime>" + lesson.getStart() + "</startTime>";
                xml += "\n\t\t\t<endTime>" + lesson.getEnd() + "</endTime>";
                xml += "\n\t\t\t<teacher>" + lesson.getTeacher() + "</teacher>";
                xml += "\n\t\t\t<room>" + (lesson.getRoom() != null ? lesson.getRoom() : "") + (lesson.getRoom2() != null ? " " + lesson.getRoom2() : "") + "</room>";
                xml += "\n\t\t</lesson>";
            }
            xml += "\n\t</day>";
        }
        xml += "\n</week>";

        return xml;
    }

    /**
     * @return the String representation of the class' fields
     */
    @Override
    public String toString() {
        return "Schedule{" +
                "weekList=" + weekList +
                ", classOfStudents=" + classOfStudents +
                ", numberOfWeeksInSemester=" + numberOfWeeksInSemester +
                ", currentWeekIndex=" + currentWeekIndex +
                ", semesterStart=" + semesterStart +
                ", semesterEnd=" + semesterEnd +
                '}';
    }
}
