package model.calendar;

import model.courses.Course;
import model.courses.Teacher;
import model.rooms.Room;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Lesson implements Serializable {
    private Course course;
    private Teacher teacher;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private Room room;
    private Room room2;

    public Lesson(Course course, Teacher teacher, LocalTime start, LocalTime end) {
        this(course,teacher, null, start, end, null, null);
    }

    public Lesson(Course course, Teacher teacher, LocalDate date, LocalTime start, LocalTime end, Room room, Room room2) {
        this.course = course;
        this.teacher = teacher;
        this.date = date;
        this.start = start;
        this.end = end;
        this.room = room;
        this.room2 = room2;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Teacher getTeacher()
    {
        return teacher;
    }

    public void setTeacher(Teacher teacher)
    {
        this.teacher = teacher;
    }

    public LocalTime getStart() {
        return start;
    }

    public Room getRoom() {
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

    public Room getRoom2() {
        return room2;
    }

    public void setRoom2(Room room2) {
        this.room2 = room2;
    }

    public void setRoom(Room room) {
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