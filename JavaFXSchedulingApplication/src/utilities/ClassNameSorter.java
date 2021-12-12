package utilities;

import model.courses.ClassOfStudents;

import java.util.Comparator;

public class ClassNameSorter implements Comparator<ClassOfStudents> {
    @Override
    public int compare(ClassOfStudents class1, ClassOfStudents class2) {
        return class1.getName().compareTo(class2.getName());
    }
}
