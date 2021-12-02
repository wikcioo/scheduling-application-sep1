package model;

import java.util.Objects;

public class Student {
    private int id;
    private int semester;
    private String _class;
    private String name;

    public Student(String name, int id, String _class, int semester) {
        this.name = name;
        this.id = id;
        this._class = _class;
        this.semester = semester;
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
}
