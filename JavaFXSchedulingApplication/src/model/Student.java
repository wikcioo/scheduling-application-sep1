package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Student {
    private int id;
    private int semester;
    private String _class;
    private String name;

    //Reads data from the head of department
    public static void readDataHOD() {
        String filename = "C:\\Users\\Aivaras\\Desktop\\Fun\\Data\\Students.txt";
        File file = new File(filename);
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
