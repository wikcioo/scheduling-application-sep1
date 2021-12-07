package model.calendar;

import model.calendar.Day;
import model.calendar.Lesson;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

public class Week implements Serializable {
    private Day[] days = new Day[7];
    private LocalDate start;
    private LocalDate end;

    public Week(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
        for (int i = 0; i < 7; i++) {
            days[i] = new Day();
            days[i].setDate(start.plusDays(i));
        }
    }

    public void addLesson(Lesson lesson, int index) {
        days[index].addLesson(lesson);
    }
    public Week filterBasedOnCourse(String course) {
        Week result = new Week(start,end);
        for (Day day : this.days) {
            for (Lesson lesson :day.getLessons()) {
                if (!lesson.getCourse().equals(course))
                    result.addLesson(lesson,day.getIndexForDay());
            }
        }
        System.out.println(result);
        return result;
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

    @Override
    public String toString() {
        return "\nWeek{" +
                "days=" + Arrays.toString(days) +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
