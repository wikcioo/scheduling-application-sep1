package model.courses;

import java.io.Serializable;
import java.util.ArrayList;

public class TeacherList implements Serializable {
    ArrayList<Teacher> teacherList;

    public TeacherList() {
        this.teacherList = new ArrayList<>();
    }

    public TeacherList(ArrayList<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public int size() {
        return teacherList.size();
    }

    public Teacher getTeacherByIndex(int index) {
        return teacherList.get(index);
    }

    public void addTeacher(Teacher teacher) {
        this.teacherList.add(teacher);
    }

    public ArrayList<Teacher> getTeacherList() {
        return teacherList;
    }

    public void removeTeacher(Teacher teacher) {
        teacherList.remove(teacher);
    }

    @Override
    public String toString() {
        String all = "";
        for (int i = 0; i < teacherList.size(); i++) {
            if (i != teacherList.size() - 1) {
                all += teacherList.get(i).getName() + ", ";
            } else {
                all += teacherList.get(i).getName();
            }
        }

        return all;
    }
}
