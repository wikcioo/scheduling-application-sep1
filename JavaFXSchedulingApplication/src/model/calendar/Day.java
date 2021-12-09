package model.calendar;

import model.calendar.Lesson;
import model.rooms.BookingTime;
import model.rooms.Room;

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
        //add date to each lesson
        for (Lesson l: this.lessons)
            l.setDate(date);
    }

    public void addLesson(Lesson lesson) {
        lesson.setDate(date);
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
        try
        {

            lesson.getRoom().unBook(
                new BookingTime(lesson.getDate(), lesson.getStart(), lesson.getEnd()));
        }
        catch (Exception e)
        {

        }
        lessons.remove(lesson);
    }

    public int getIndexForDay() {
        return date.getDayOfWeek().getValue() - 1;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<Lesson> copyLessons() {
        return (ArrayList<Lesson>) lessons.clone();
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
