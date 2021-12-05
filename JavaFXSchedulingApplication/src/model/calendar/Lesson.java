package model.calendar;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

public class Lesson implements Serializable {
    private String course;
    private LocalTime start;
    private LocalTime end;

    public Lesson(String course, LocalTime time,LocalTime end) {
        this.course = course;
        this.start = time;
        this.end = end;
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