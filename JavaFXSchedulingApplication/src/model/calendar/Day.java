package model.calendar;

import model.rooms.BookingTime;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Day implements Serializable {
    private ArrayList<Lesson> lessons;
    private LocalDate date;

    public Day() {
        this.lessons = new ArrayList<>(); // 2 time units
        this.date = null; // 1 time unit
    }

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

    /**
     *  This method sorts lesson objects according to start time in ascending order
     */
    private void sortLessons() {
        // iterate through the entire lessons ArrayList except the last element
        for (int i = 0; i < lessons.size() - 1; i++) { // This loop will run n times

            // We initialize 'wasChanged' variable to false
            boolean wasChanged = false; // This takes 2 time units

            // for each iteration loop again through the entire ArrayList minus one minus i
            for (int j = 0; j < lessons.size() - i - 1; j++) { // For each iteration it will run n times

                // Check if one lesson is after another
                if (lessons.get(j).getStart().isAfter(lessons.get(j + 1).getStart())) { // This takes 5 time units
                    // Call to swapElements
                    swapElements(j, j + 1); // This takes 6 time units

                    // If elements were swapped, then we assign true to 'wasChanged' variable
                    wasChanged = true; // This takes 1 time unit
                }
            }

            // We break from the for loop, thus returning from the method
            if (!wasChanged) { // This takes 1 time unit
                break; // This takes 1 time unit
            }
        }

        // We have no recursion, so we do not need a base case
        // T(n) = n * (12n + 4)
        // T(n) = O(n^2) - worst case scenario
        // T(n) = O(n)   - best case scenario
    }

    private void swapElements(int a, int b) { // 6 time units
        Lesson temp = lessons.get(a); // 3 time units
        lessons.set(a, lessons.get(b)); // 2 time units
        lessons.set(b, temp); // 1 time unit
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
            lessons.add(new Lesson(lesson.getCourse(),lesson.getTeacher(), date, lesson.getStart(), lesson.getEnd(), lesson.getRoom(), lesson.getRoom2()));
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
