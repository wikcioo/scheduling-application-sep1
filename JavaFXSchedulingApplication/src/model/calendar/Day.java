package model.calendar;

import model.rooms.BookingTime;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

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

    private void sortLessons() {
        for (int i = 0; i < lessons.size() - 1; i++) {
            boolean wasChanged = false;
            for (int j = 0; j < lessons.size() - i - 1; j++) {
                if (lessons.get(j).getStart().isAfter(lessons.get(j + 1).getStart())) {
                    swapElements(j, j + 1);
                    wasChanged = true;
                }
            }

            if (!wasChanged) break;
        }
    }

    private void swapElements(int a, int b) {
        Lesson temp = lessons.get(a);
        lessons.set(a, lessons.get(b));
        lessons.set(b, temp);
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
