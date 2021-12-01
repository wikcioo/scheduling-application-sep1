package model;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentList {
    private ArrayList<Student> studentsList;

    public StudentList() {
        studentsList = new ArrayList<>();
    }

    public void addStudent(Student student) {
        studentsList.add(student);
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
            for (String s : token) {
                System.out.print(s.trim()+" ");

            }
            System.out.println();
        }
        in.close();

    }
}
