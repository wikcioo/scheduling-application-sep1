package model;

import utilities.Util;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Semester {
    private final ArrayList<Week> weekList;
    private final int numberOfWeeksInSemester;
    private int currentWeekIndex;
    private final LocalDate semesterStart;
    private final LocalDate semesterEnd;

    public Semester(LocalDate semesterStart, LocalDate semesterEnd) {
        this.weekList = new ArrayList<>();
        this.semesterStart = semesterStart;
        this.semesterEnd = semesterEnd;
        this.numberOfWeeksInSemester = getNumberOfWeeksBetween();
        initializeAllWeeks();
        initializeCurrentWeekIndex();
    }
    public boolean hasNextWeek(){
        return weekList.size() - 1 >currentWeekIndex;
    }
    public boolean hasPreviousWeek(){
        return currentWeekIndex > 0;
    }

    public Week getCurrentWeek() {
        return weekList.get(currentWeekIndex);
    }

    public int getCurrentWeekIndex() {
        return currentWeekIndex;
    }

    public void goNextWeek() {
        currentWeekIndex++;
    }

    public void goPreviousWeek() {
        currentWeekIndex--;
    }

    public ArrayList<Week> getWeekList() {
        return this.weekList;
    }

    public int getNumberOfWeeksBetween() {
        int i = 0;
        while (semesterStart.plusWeeks(i).compareTo(semesterEnd) < 0) {
            i++;
        }

        return i;
    }

    public void initializeCurrentWeekIndex() {
        LocalDate currentWeekMonday = Util.getMonday();
        for (int i = 0; i < weekList.size(); i++) {
            if (weekList.get(i).getStart().equals(currentWeekMonday)) {
                currentWeekIndex = i;
                break;
            }
        }
    }

    public int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    public void initializeAllWeeks() {
        for (int i = 0; i < this.numberOfWeeksInSemester; i++) {
            weekList.add(new Week(semesterStart.plusWeeks(i), semesterStart.plusWeeks(i).plusDays(6)));
        }
    }

    public void readData() {
        String filename = "res/saved-data/weekList.bin";
        ObjectInputStream in = null;
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
            Week week1;
            int index = 0;
            while((week1 = (Week) in.readObject())!=null){
                this.weekList.set(index, week1);
                index++;
            }
        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found in the resources");
        }
        catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException | NullPointerException e) {
                //e.printStackTrace();
            }
        }
    }

    //Saves data about weeks to binary file
    public void saveData() {
        String filename = "res/saved-data/weekList.bin";

        ObjectOutputStream out = null;
        try {
            File file = new File(filename);
            FileOutputStream fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            for(Week week : this.weekList){
                out.writeObject(week);
            }
        } catch (IOException e) {
            System.out.println("Exception: " + filename);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "Semester{" +
                "weekList=" + weekList +
                ", numberOfWeeksInSemester=" + numberOfWeeksInSemester +
                ", currentWeekIndex=" + currentWeekIndex +
                ", semesterStart=" + semesterStart +
                ", semesterEnd=" + semesterEnd +
                '}';
    }
}
