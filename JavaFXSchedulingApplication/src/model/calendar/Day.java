package model.calendar;

import model.rooms.BookingTime;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

public class Day implements Serializable {
    private ArrayList<Lesson> lessons = new ArrayList<>();
    private LocalDate date;

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
        for (Lesson l : this.lessons)
            l.setDate(date);
    }

    public boolean isValidDataForTime(Lesson lesson) {
        for (Lesson l : lessons) {
            if (overlaps(lesson, l)) return false;
        }
        return true;
    }

    private boolean overlaps(Lesson lesson, Lesson lesson2) {
        LocalTime startLesson1 = lesson.getStart();
        LocalTime endLesson1 = lesson.getEnd();
        LocalTime startLesson2 = lesson2.getStart();
        LocalTime endLesson2 = lesson2.getEnd();
        return (startLesson1.isBefore(endLesson2)) && (endLesson1.isAfter(startLesson2));
    }

    public void addLesson(Lesson lesson) {
        lesson.setDate(date);
        lessons.add(lesson);
        sortLessons();
    }

    public void sortLessons() {
        lessons.sort(new Comparator<Lesson>() {
            @Override
            public int compare(Lesson o1, Lesson o2) {
                return o1.getStart().compareTo(o2.getStart());
            }
        });
    }

    public void removeLesson(Lesson lesson) {
        try {
            lesson.getRoom().unBook(new BookingTime(lesson.getDate(), lesson.getStart(), lesson.getEnd()));
        } catch (Exception e) {

        }

        lessons.remove(lesson);
    }

    public int getIndexForDay() {
        return date.getDayOfWeek().getValue() - 1;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<Lesson> copyLessons(LocalDate date) {
        ArrayList<Lesson> lessons = new ArrayList<>();

        for (Lesson lesson : this.lessons) {
            lessons.add(new Lesson(lesson.getCourse(), date, lesson.getStart(), lesson.getEnd(), lesson.getRoom(), lesson.getRoom2()));
        }

        return lessons;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Day{" +
                "lessons=" + lessons +
                ", date=" + date +
                '}';
    }
}
