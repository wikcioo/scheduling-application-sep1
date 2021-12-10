package model.courses;

import utilities.ClassNameSorter;

import java.io.Serializable;
import java.util.ArrayList;

public class ClassList implements Serializable {
    private ArrayList<ClassOfStudents> classList;
    private int currentlySelectedClass;

    public int getCurrentlySelectedClass() {
        return currentlySelectedClass;
    }

    public void setCurrentlySelectedClass(int currentlySelectedClass) {
        this.currentlySelectedClass = currentlySelectedClass;
    }

    public ClassList() {
        this.classList = new ArrayList<>();
    }

    public void addClass(ClassOfStudents _class) {
        classList.add(_class);
        classList.sort(new ClassNameSorter());
    }

    public void removeClass(ClassOfStudents _class) {
        try {
            classList.remove(_class);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void sortClassesByName() {
        classList.sort(new ClassNameSorter());
    }

    public ArrayList<ClassOfStudents> getClasses() {
        return classList;
    }

    public ArrayList<ClassOfStudents> copyClasses() {
        return new ArrayList<ClassOfStudents>(classList);
    }
}
