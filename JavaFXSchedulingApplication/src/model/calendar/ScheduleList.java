package model.calendar;

import model.courses.ClassOfStudents;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ScheduleList implements Serializable {
    private ArrayList<Schedule> schedules;
    private Schedule currentSchedule;

    public ScheduleList() {
        schedules = new ArrayList<>();
        currentSchedule = null;
    }

    public Schedule getCurrentSchedule() {
        return currentSchedule;
    }

    public void setCurrentSchedule(Schedule currentSchedule) {
        this.currentSchedule = currentSchedule;
    }

    public void addSchedule(Schedule schedule) {
        if (getScheduleByClass(schedule.getClassOfStudents(), false) != null) {
            throw new IllegalArgumentException("Class already has a schedule");
        } else schedules.add(schedule);
    }

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

    public Schedule getScheduleByClass(ClassOfStudents class1, boolean createIfNoScheduleFound) {
        for (Schedule schedule : schedules) {
            if (Objects.equals(schedule.getClassOfStudents(), class1))
                return schedule;
        }
        //If schedule doesn't exist for certain class, create a new one schedule

        if (createIfNoScheduleFound) {
            Schedule schedule = new Schedule(LocalDate.of(2021, 8, 30),
                    LocalDate.of(2021, 12, 17),
                    class1);

            schedules.add(schedule);
            return schedule;
        }
        return null;
    }

    @Override
    public String toString() {
        return "ScheduleList{" +
                "schedules=" + schedules +
                '}';
    }
}
