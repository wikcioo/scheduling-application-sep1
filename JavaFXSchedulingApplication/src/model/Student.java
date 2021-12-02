package model;

import java.io.Serializable;
import java.util.Objects;

public class Student implements Serializable {
    private int id;
    private int semester;
    private String _class;
    private String name;
    private boolean isExchange;

    public Student(String name, int id, String _class, int semester) {
        this.name = name;
        this.id = id;
        this._class = _class;
        this.semester = semester;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExchange(boolean exchange) {
        isExchange = exchange;
    }

    public int getId() {
        return id;
    }

    public int getSemester() {
        return semester;
    }

    public String get_class() {
        return _class;
    }

    public String getName() {
        return name;
    }

    public boolean isExchange() {
        return isExchange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && semester == student.semester && Objects.equals(_class, student._class) && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, semester, _class, name);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", semester=" + semester +
                ", _class='" + _class + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
