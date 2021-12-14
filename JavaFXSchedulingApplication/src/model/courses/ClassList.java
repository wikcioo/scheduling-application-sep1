package model.courses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class ClassList implements Serializable {
    private ArrayList<ClassOfStudents> classList;
    private int currentlySelectedClass;

    public ClassList() {
        this.classList = new ArrayList<>();
    }

    public int getCurrentlySelectedClass() {
        return currentlySelectedClass;
    }

    public void setCurrentlySelectedClass(int currentlySelectedClass) {
        this.currentlySelectedClass = currentlySelectedClass;
    }

    public void addClass(ClassOfStudents _class) {
        classList.add(_class);
        classList.sort(new Comparator<ClassOfStudents>() {
            @Override
            public int compare(ClassOfStudents class1, ClassOfStudents class2) {
                return class1.getName().compareTo(class2.getName());
            }
        });
    }

    public void removeClass(ClassOfStudents _class) {
        try {
            classList.remove(_class);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ClassOfStudents> getClasses() {
        return classList;
    }

    public ArrayList<ClassOfStudents> copyClasses() {
        return new ArrayList<ClassOfStudents>(classList);
    }
}
