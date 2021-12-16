package model.calendar;

import model.courses.ClassOfStudents;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is a representation of a list of schedules. It includes the array list of schedules, current schedule,
 * as well as the semester start and end dates. It implements Serializable interface for persistence purposes.
 */
public class ScheduleList implements Serializable {
    private ArrayList<Schedule> schedules;
    private Schedule currentSchedule;
    private LocalDate semesterStart;
    private LocalDate semesterEnd;

    /**
     * Calls the overloaded constructor with null values for the parameters.
     */
    public ScheduleList() {
        this(null, null);
    }

    /**
     * Creates a new array list, sets current schedule to null and assigns start and end date to the given parameter values.
     *
     * @param semesterStart the start date of a semester
     * @param semesterEnd the end date of a semester
     */
    public ScheduleList(LocalDate semesterStart, LocalDate semesterEnd) {
        schedules = new ArrayList<>();
        currentSchedule = null;
        this.semesterStart = semesterStart;
        this.semesterEnd = semesterEnd;
    }

    /**
     * @return the current schedule
     */
    public Schedule getCurrentSchedule() {
        return currentSchedule;
    }

    /**
     * @param currentSchedule the current schedule to be set
     */
    public void setCurrentSchedule(Schedule currentSchedule) {
        this.currentSchedule = currentSchedule;
    }

    /**
     * @return the array list of all lessons inside all the schedules that are in the array list
     */
    public ArrayList<Lesson> getAllLessons() {
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (Schedule s : schedules) {
            for (Week w : s.getWeekList()) {
                for (Day d : w.getDays())
                    lessons.addAll(d.getLessons());
            }
        }
        return lessons;
    }

    /**
     * @param class1 the class of students
     * @param createIfNoScheduleFound if the schedule doesn't exist for a certain class, then create a new schedule
     * @return found schedule. null if not found
     */
    public Schedule getScheduleByClass(ClassOfStudents class1, boolean createIfNoScheduleFound) {
        for (Schedule schedule : schedules) {
            if (Objects.equals(schedule.getClassOfStudents(), class1))
                return schedule;
        }

        //If schedule doesn't exist for certain class, create a new one schedule
        if (createIfNoScheduleFound) {
            Schedule schedule = new Schedule(semesterStart, semesterEnd, class1);
            schedules.add(schedule);
            return schedule;
        }

        return null;
    }

    /**
     * @return the String representation of the class' fields
     */
    @Override
    public String toString() {
        return "ScheduleList{" +
                "schedules=" + schedules +
                '}';
    }
}
