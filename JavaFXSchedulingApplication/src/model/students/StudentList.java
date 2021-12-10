package model.students;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class StudentList implements Serializable{
    private ArrayList<Student> studentList;

    public StudentList() {
        studentList = new ArrayList<>();
    }

    public StudentList(ArrayList<Student> studentList) {
        this.studentList = new ArrayList<>();
        for(Student student : studentList) {
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


    public void readStudentListFromBinFile() {
        String filename = "res/saved-data/studentList.bin";
        ObjectInputStream in = null;
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
            Student student;
            while((student = (Student) in.readObject()) != null){
                this.studentList.add(student);
                System.out.println(student);
            }

        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found in the resources");
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException | NullPointerException e) {
                //e.printStackTrace();
            }
        }
    }

    public void writeStudentListToBinFile(String _file) {
        String filename;
        filename = Objects.requireNonNullElse(_file, "res/saved-data/studentList.bin");

        ObjectOutputStream out = null;
        try {
            File file = new File(filename);
            FileOutputStream fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            for(Student student : this.studentList){
                out.writeObject(student);
            }
        } catch (IOException e) {
            System.out.println("Exception: " + filename);
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readStudentFromTXTFile(File file) {
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
                String _class = token[1].trim();
                int id = Integer.parseInt(token[2]);
                String name = token[3].trim();
                Student student = new Student(name, id, _class, semester);
                if (!isValidStudent(student)) {
                    throw new NumberFormatException();
                }
                System.out.println(student);
                studentList.add(student);
            } catch (NumberFormatException e) {
                System.out.println("Wrong format, skipping...");
                //e.printStackTrace();
            }
        }
        in.close();
    }

    private boolean isValidStudent(Student student) {
        if (!student.getName().matches("[A-Za-z ]+")) return false;
        if (!student.get_class().matches("[A-Za-z]")) return false;
        for (Student s : this.studentList) {
            if (s.getId() == student.getId()) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StudentList{" +
            "studentList=" + studentList +
            '}';
    }
}