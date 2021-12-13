package model.courses;

import model.students.StudentList;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private String title;
    private TeacherList teacherList;
    private ClassOfStudents classOfStudents;
    private StudentList participants;
    private String hexColor = "black";

    public Course(String title, ArrayList<Teacher> teacherList, ClassOfStudents classOfStudents) {
        this.title = title;
        this.teacherList = new TeacherList(teacherList);

        this.classOfStudents = classOfStudents;
        if (classOfStudents != null) {
            initStudents();
        }
    }

    public Course(String title, ArrayList<Teacher> teacherList) {
        this(title, teacherList, null);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setClassOfStudents(ClassOfStudents classOfStudents) {
        this.classOfStudents = classOfStudents;
    }

    public void initStudents() {
        try {
            this.participants = classOfStudents.getStudentList().copy();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public TeacherList getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(ArrayList<Teacher> teacherList) {
        this.teacherList = new TeacherList(teacherList);
    }

    public ClassOfStudents getClassOfStudents() {
        return classOfStudents;
    }

    public StudentList getParticipants() {
        return participants;
    }

    public String getClassName() {
        return this.classOfStudents.getName();
    }

    public String getTeacherName() {
        return teacherList.toString();
    }

    public void setParticipants(StudentList participants) {
        this.participants = participants;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    @Override
    public String toString() {
        return title;
    }
}
