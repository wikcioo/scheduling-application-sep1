package model.calendar;

import model.calendar.Lesson;

import java.io.Serializable;
import java.time.LocalDate;
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
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        sortLessons();
    }

    //Sorts lessons by start date
    public void sortLessons(){
        lessons.sort(new Comparator<Lesson>() {
            @Override
            public int compare(Lesson o1, Lesson o2) {
                return o1.getStart().compareTo(o2.getStart());
            }
        });
    }

    public void removeLesson(Lesson lesson){
        lessons.remove(lesson);
    }

    public int getIndexForDay() {
        return date.getDayOfWeek().getValue() - 1;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
