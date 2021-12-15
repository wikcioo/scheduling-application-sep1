package model.students;

import utilities.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The purpose of this class is to store an ArrayList of Students objects in
 * order to manage them
 */
public class StudentList implements Serializable {
    private ArrayList<Student> studentList;

    /**
     * The purpose of this constructor is to initialize an ArrayList of students
     */
    public StudentList() {
        studentList = new ArrayList<>();
    }

    /**
     * The purpose of this constructor is to initialize an ArrayList of students
     * with an existing ArrayList
     */
    public StudentList(ArrayList<Student> studentList) {
        this.studentList = new ArrayList<>();
        for (Student student : studentList) {
            this.studentList.add(student);
        }
    }

    /**
     * Return a copy of all the students
     */
    public StudentList copy() {
        return new StudentList(this.studentList);
    }

    /**
     * The purpose of this method is to set the ArrayList of Students to an
     * existing one
     */
    public void setStudentList(ArrayList<Student> studentList) {
        this.studentList = studentList;
    }

    /**
     Returns all the students
     */
    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    /**
     * Returns a student with a specific index
     */
    public Student getStudent(int index) {
        return studentList.get(index);
    }

    /**
     * The purpose of this method is to add a student to the studentList ArrayList
     */
    public void addStudent(Student student) {
        studentList.add(student);
    }

    /**
     * The purpose of this method is to remove a student from the studentList ArrayList
     * using its name
     */
    public void removeStudent(Student student) {
        studentList.remove(student);
    }

    /**
     * The purpose of this method is to remove a student from the studentList ArrayList
     *      * using its index
     */
    public void removeStudent(int index) {
        studentList.remove(index);
    }

    /**
     * The purpose of this method is to return all the students which belongs to
     * a specific class
     */
    public ArrayList<Student> getStudentsByClass(String _class) {
        ArrayList<Student> s = new ArrayList<>();
        for (Student student : this.studentList) {
            if (student.get_class().equalsIgnoreCase(_class)) {
                s.add(student);
            }
        }

        return s;
    }

    /**
     The purpose of this method is to return all the students which has a specific
     name
     */
    public ArrayList<Student> getStudentsByName(String name) {
        ArrayList<Student> s = new ArrayList<>();
        for (Student student : this.studentList) {
            if (student.getName().contains(name)) {
                s.add(student);
            }
        }

        return s;
    }

    /**
     * The purpose of this method is to read the student data from the txt file
     */
    public void readStudentFromTXTFile(File file) {
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (in.hasNext()) {
            String line = in.nextLine();
            String[] token = line.split(",");
            try {
                int semester = Integer.parseInt(token[0]);
                String _class = token[1].trim();
                int id = Integer.parseInt(token[2]);
                String name = token[3].trim();
                Student student = new Student(name, id, _class, semester);
                if (!isValidStudent(student)) {
                    throw new NumberFormatException();
                }
                studentList.add(student);
            } catch (NumberFormatException e) {
                Logger.error("Wrong format, skipping...");
            }
        }

        in.close();
    }

    /**
     * The purpose of this method is to check if the data from a student is correct
     * If the data is correct it will return true, otherwise false
     */
    public boolean isValidStudent(Student student) {
        if (!student.getName().matches("[A-Za-z ]+")) return false;
        if (!student.get_class().matches("[A-Za-z]")) return false;
        for (Student s : this.studentList) {
            if (s.getId() == student.getId()) return false;
        }
        if(student.getId()<=0) return false;

        int number = student.getId();
        int numDigits = 0;
        while (number != 0) {
            number /= 10;
            numDigits++;
        }

        if (numDigits != 6) return false;

        return true;
    }

    /**
     * Return a list of all students
     */
    @Override
    public String toString() {
        return "StudentList{" +
                "studentList=" + studentList +
                '}';
    }
}