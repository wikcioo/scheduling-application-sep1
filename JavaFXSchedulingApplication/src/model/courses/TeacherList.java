package model.courses;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The purpose of this class is to store an ArrayList of Teacher objects in order
 * to manage them
 */
public class TeacherList implements Serializable {
    ArrayList<Teacher> teacherList;

    /**
     * The purpose of this constructor is to initialize the teacherList ArrayList
     */
    public TeacherList() {
        this.teacherList = new ArrayList<>();
    }

    /**
     * The purpose of this constructor is to initialize the teacherList ArrayList
     * with a given arrayList of teacher objects
     */
    public TeacherList(ArrayList<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    /**
     * Returns the number of teachers
     */
    public int size() {
        return teacherList.size();
    }

    /**
     * Return the teacher with a specific index
     */
    public Teacher getTeacherByIndex(int index) {
        return teacherList.get(index);
    }

    /**
     * The purpose of this method is to add a new teacher to the teacherList ArrayList
     */
    public void addTeacher(Teacher teacher) {
        this.teacherList.add(teacher);
    }

    /**
     * Returns a list of all teacher object
     */
    public ArrayList<Teacher> getTeacherList() {
        return teacherList;
    }

    /**
     * The purpose of this method is to remove a new teacher from the teacherList ArrayList
     */
    public void removeTeacher(Teacher teacher) {
        teacherList.remove(teacher);
    }

    /**
     * Returns all teachers from a course
     */
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
