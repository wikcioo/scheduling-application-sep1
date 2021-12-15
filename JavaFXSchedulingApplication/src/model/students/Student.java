package model.students;

import java.io.Serializable;
import java.util.Objects;

/**
 * The purpose of this class is to store the information about a person
 */
public class Student extends Person implements Serializable {
    private int semester;
    private String _class;

    /**
     * The purpose of this constructor is to initialize all instance variables
     * of the student object and generate a via email for that student instead
     * of using its email
     * @param id the id of the student
     * @param name the name of the student
     * @param _class the class of the student
     */
    public Student(String name, int id, String _class, int semester) {
        super(name, id + "@via.dk", id);
        this._class = _class;
        this.semester = semester;
    }

    /**
     * The purpose of this constructor is to initialize all instance variables
     * of the student object
     * @param _class the class of the student
     * @param name the name of the student
     * @param id the id of the student
     */
    public Student(String name, String email, int id, String _class, int semester) {
        super(name, email, id);
        this._class = _class;
        this.semester = semester;
    }

    /**
     * @return the int value of a semester
     */
    public int getSemester() {
        return semester;
    }

    /**
     * The purpose of this method is to set in which semester a student it
     * @param semester the semester in which the student is
     */
    public void setSemester(int semester) {
        this.semester = semester;
    }

    /**
     * @return the class in which the student belongs
     */
    public String get_class() {
        return _class;
    }

    /**
     * The purpose of this method is to set the class of the student
     * @param _class  the class of the student
     */
    public void set_class(String _class) {
        this._class = _class;
    }

    /**
     * @return the name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * The purpose of this method is to set the name of the student
     * @param name the name of the student
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email of the student
     */
    public String getEmail() {
        return email;
    }

    /**
     * The purpose of this method is to set the email of the student
     * @param email the email of the student
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the id of the student
     */
    public int getId() {
        return id;
    }

    /**
     * The purpose of this method is to set the id of the student
     * @param id  the id of the student
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The purpose of this method is to compare two students and to return true
     * if the name, id, class and semester of both students and false otherwise
     * @param o the object that is compared with the student object
     * @return  true if they are the same, false otherwise
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return getId() == student.getId() && semester == student.semester && Objects.equals(_class, student._class) && Objects.equals(getName(), student.getName());
    }

    /**
     * @return all the information about a student
     */
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