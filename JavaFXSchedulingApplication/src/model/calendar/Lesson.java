package model.calendar;

import model.courses.Course;
import model.courses.Teacher;
import model.rooms.Room;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * This class is a representation of a lesson that contains information on which course it is, the teacher teaching
 * the lesson, the date, start and end time and the room(s) it is held in. It implements Serializable interface for
 * persistence purposes.
 */
public class Lesson implements Serializable {
    private Course course;
    private Teacher teacher;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private Room room;
    private Room room2;

    /**
     * Initializes variables with given parameters and sets date, room, and room2 to null.
     *
     * @param course the course
     * @param teacher the teacher teaching the lesson
     * @param start the starting time
     * @param end the ending time
     */
    public Lesson(Course course, Teacher teacher, LocalTime start, LocalTime end) {
        this(course,teacher, null, start, end, null, null);
    }

    /**
     * Initializes variables with given parameters. Called by the overloaded constructor that passes nulls for
     * the date, room and room2 fields.
     *
     * @param course the course
     * @param teacher the teacher teaching the lesson
     * @param date the date of the lesson
     * @param start the starting time
     * @param end the ending time
     * @param room the room the lesson is held in
     * @param room2 the optional second room the lesson is held in
     */
    public Lesson(Course course, Teacher teacher, LocalDate date, LocalTime start, LocalTime end, Room room, Room room2) {
        this.course = course;
        this.teacher = teacher;
        this.date = date;
        this.start = start;
        this.end = end;
        this.room = room;
        this.room2 = room2;
    }

    /**
     * @return the date of the lesson
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date of the lesson to be set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return the course that the lesson is attached to
     */
    public Course getCourse() {
        return course;
    }

    /**
     * @param course the course to be assigned to the lesson
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * @return the teacher teaching the lesson
     */
    public Teacher getTeacher()
    {
        return teacher;
    }

    /**
     * @param teacher the teacher to be set to teach the lesson
     */
    public void setTeacher(Teacher teacher)
    {
        this.teacher = teacher;
    }

    /**
     * @return the time of the beginning of the lesson
     */
    public LocalTime getStart() {
        return start;
    }

    /**
     * @param start the starting time of the lesson
     */
    public void setStart(LocalTime start) {
        this.start = start;
    }

    /**
     * @return the ending time of the lesson
     */
    public LocalTime getEnd() {
        return end;
    }

    /**
     * @param end the ending time of the lesson
     */
    public void setEnd(LocalTime end) {
        this.end = end;
    }

    /**
     * @param time the starting time of the lesson
     */
    public void setTime(LocalTime time) {
        this.start = time;
    }

    /**
     * @return the second room that the lesson might be held in
     */
    public Room getRoom2() {
        return room2;
    }

    /**
     * @param room2 the second room that the lesson might be held in
     */
    public void setRoom2(Room room2) {
        this.room2 = room2;
    }

    /**
     * @return the room that the lesson is held in
     */
    public Room getRoom() {
        return room;
    }

    /**
     * @param room the room that the lesson is held in
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * @param o the object to be compared against
     * @return true if the object o has the same data as this lesson
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(course, lesson.course) && Objects.equals(start, lesson.start) && Objects.equals(end, lesson.end);
    }

    /**
     * @return the hash code of the object based on the data of it's fields
     */
    @Override
    public int hashCode() {
        return Objects.hash(course, start, end);
    }

    /**
     * @return the String representation of the class' fields
     */
    @Override
    public String toString() {
        return "Lesson{" +
                "course='" + course + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}