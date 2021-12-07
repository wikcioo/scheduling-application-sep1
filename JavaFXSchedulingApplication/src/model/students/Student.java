package model.students;

import java.io.Serializable;
import java.util.Objects;

public class Student extends Person implements Serializable {
    private int semester;
    private String _class;
    private boolean isExchange;

    public Student(String name, int id, String _class, int semester) {
        super(name, id + "@via.dk", id);
        this._class = _class;
        this.semester = semester;
    }

    public Student(String name, String email, int id, String _class, int semester) {
        super(name, email, id);
        this._class = _class;
        this.semester = semester;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public boolean isExchange() {
        return isExchange;
    }

    public void setExchange(boolean exchange) {
        isExchange = exchange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return getId() == student.getId() && semester == student.semester && Objects.equals(_class, student._class) && Objects.equals(getName(), student.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), semester, _class, getName());
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", semester=" + semester +
            ", _class='" + _class + '\'' +
            ", name='" + getName() + '\'' +
            '}';
    }
}