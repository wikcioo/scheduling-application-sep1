package model.calendar;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * This class is a representation of a single week in that holds data about 7 days in the week and also the start and
 * the end date of a week period. It implements Serializable interface for persistence purposes.
 */
public class Week implements Serializable {
    private final Day[] days = new Day[7];
    private final LocalDate start;
    private final LocalDate end;

    /**
     * Initializes values of start and end with constructor parameters
     * Iterates through days array and initializes them. After that, it sets the appropriate date based on the
     * start variable.
     *
     * @param start start date of the week
     * @param end end date of the week
     */
    public Week(LocalDate start, LocalDate end) { // Takes 19 + 28 + 14 = 40 + 17 + 4 = 61 time units
        this.start = start; // 1 time unit
        this.end = end; // 1 time unit
        for (int i = 0 /* 2 time units */; i < 7 /* 8 time units */; i++ /* 7 time units */) { // This takes 17 time units
            days[i] = new Day(); // 7 * 4 time units
            days[i].setDate(start.plusDays(i)); // 7 * 2 time units
        }
    }

    /**
     * Add a new lesson to the days array at an index.
     *
     * @param lesson the lesson to be added to the days array
     * @param index the index at which the lesson is supposed to be added
     */
    public void addLesson(Lesson lesson, int index) {
        days[index].addLesson(lesson);
    }

    /**
     * @return the start date of a week
     */
    public LocalDate getStart() {
        return start;
    }

    /**
     * @return the end date of a week
     */
    public LocalDate getEnd() {
        return end;
    }

    /**
     * @return the week array
     */
    public Day[] getDays() {
        return days;
    }

    /**
     * Copies week's lessons to the current week.
     *
     * @param week the week whose lessons will be copied to the current week
     */
    public void setWeekLessons(Week week) {
        Day[] copiedDays = week.copyDays();
        for (int i = 0; i < 7; i++) {
            this.days[i].setLessons(copiedDays[i].copyLessons(days[i].getDate()));
        }
    }

    /**
     * Returns the cloned day array.
     *
     * @return {@code days} array
     */
    public Day[] copyDays() {
        return days.clone();
    }


    /**
     * @return the copy of a week
     */
    public Week copy() {
        Week newWeek = new Week(start, end);
        newWeek.setWeekLessons(this);
        return newWeek;
    }

    /**
     * @return the String representation of the class' fields
     */
    @Override
    public String toString() {
        return "\nWeek{" +
                "days=" + Arrays.toString(days) +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
