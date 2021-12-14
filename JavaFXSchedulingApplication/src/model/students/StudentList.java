package model.students;

import utilities.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentList implements Serializable {
    private ArrayList<Student> studentList;

    public StudentList() {
        studentList = new ArrayList<>();
    }

    public StudentList(ArrayList<Student> studentList) {
        this.studentList = new ArrayList<>();
        for (Student student : studentList) {
            this.studentList.add(student);
        }
    }

    public StudentList copy() {
        return new StudentList(this.studentList);
    }

    public void setStudentList(ArrayList<Student> studentList) {
        this.studentList = studentList;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public Student getStudent(int index) {
        return studentList.get(index);
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }

    public void removeStudent(Student student) {
        studentList.remove(student);
    }

    public void removeStudent(int index) {
        studentList.remove(index);
    }

    public ArrayList<Student> getStudentsByClass(String _class) {
        ArrayList<Student> s = new ArrayList<>();
        for (Student student : this.studentList) {
            if (student.get_class().equalsIgnoreCase(_class)) {
                s.add(student);
            }
        }

        return s;
    }

    public ArrayList<Student> getStudentsByName(String name) {
        ArrayList<Student> s = new ArrayList<>();
        for (Student student : this.studentList) {
            if (student.getName().contains(name)) {
                s.add(student);
            }
        }

        return s;
    }

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

    @Override
    public String toString() {
        return "StudentList{" +
                "studentList=" + studentList +
                '}';
    }
}