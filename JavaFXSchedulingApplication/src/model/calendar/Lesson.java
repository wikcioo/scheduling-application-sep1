package model.calendar;

import model.rooms.Room;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Lesson implements Serializable {
    private String course;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private Room room;

    public Lesson(String course, LocalTime time,LocalTime end) {
        this.course = course;
        this.start = time;
        this.end = end;
    }

    public Lesson(String course, LocalDate date, LocalTime start, LocalTime end, Room room)
    {
        this.course = course;
        this.date = date;
        this.start = start;
        this.end = end;
        this.room = null;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public LocalTime getStart() {
        return start;
    }

    public Room getRoom()
    {
        return room;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public void setTime(LocalTime time) {
        this.start = time;
    }

    public void setRoom(Room room)
    {

        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(course, lesson.course) && Objects.equals(start, lesson.start) && Objects.equals(end, lesson.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, start, end);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "course='" + course + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}