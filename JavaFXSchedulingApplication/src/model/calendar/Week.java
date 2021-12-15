package model.calendar;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

public class Week implements Serializable {
    private final Day[] days = new Day[7];
    private final LocalDate start;
    private final LocalDate end;

    public Week(LocalDate start, LocalDate end) { // Takes 19 + 28 + 14 = 40 + 17 + 4 = 61 time units
        this.start = start; // 1 time unit
        this.end = end; // 1 time unit
        for (int i = 0 /* 2 time units */; i < 7 /* 8 time units */; i++ /* 7 time units */) { // This takes 17 time units
            days[i] = new Day(); // 7 * 4 time units
            days[i].setDate(start.plusDays(i)); // 7 * 2 time units
        }
    }

    public void addLesson(Lesson lesson, int index) {
        days[index].addLesson(lesson);
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public Day[] getDays() {
        return days;
    }

    public void setWeekLessons(Week week) {
        Day[] copiedDays = week.copyDays();
        for (int i = 0; i < 7; i++) {
            this.days[i].setLessons(copiedDays[i].copyLessons(days[i].getDate()));
        }
    }

    public Day[] copyDays() {
        return days.clone();
    }

    public Week copy() {
        Week newWeek = new Week(start, end);
        newWeek.setWeekLessons(this);
        return newWeek;
    }

    @Override
    public String toString() {
        return "\nWeek{" +
                "days=" + Arrays.toString(days) +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
