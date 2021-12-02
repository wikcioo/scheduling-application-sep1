package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentList {
    private ArrayList<Student> studentsList;

    public StudentList() {
        studentsList = new ArrayList<>();
    }

    public void setStudentsList(ArrayList<Student> studentsList) {
        this.studentsList = studentsList;
    }

    public ArrayList<Student> getStudentsList() {
        return studentsList;
    }

    public void addStudent(Student student) {
        studentsList.add(student);
    }

    public void removeStudent(Student student) {
        studentsList.remove(student);
    }

    public static void readStudentFromBinFile() {

    }

    public static void readStudentFromTXTFile(File file) {
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (in.hasNext()){
            String line = in.nextLine();
            String[] token = line.split(",");
            try {
                int semester = Integer.parseInt(token[0]);
                String _class = token[1];
                int id = Integer.parseInt(token[2]);
                String name = token[3];
                if (!name.matches("[A-Za-z ]+")) {
                    throw new NumberFormatException();
                }

//                System.out.println("name: " + name + ", id: " + id + ", class: " + _class + ", semester: " + semester);
            } catch (NumberFormatException e) {
                System.out.println("Wrong format, skipping...");
                e.printStackTrace();
            }
        }
        in.close();
    }
}
