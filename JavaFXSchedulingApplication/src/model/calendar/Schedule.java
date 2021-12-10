package model.calendar;

import model.courses.ClassOfStudents;
import utilities.Util;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Schedule implements Serializable {
    private final ArrayList<Week> weekList;
    private ClassOfStudents classOfStudents;
    private final int numberOfWeeksInSemester;
    private int currentWeekIndex;
    private final LocalDate semesterStart;
    private final LocalDate semesterEnd;

    public Schedule(LocalDate semesterStart, LocalDate semesterEnd, ClassOfStudents classOfStudents) {
        this.weekList = new ArrayList<>();
        this.classOfStudents = classOfStudents;
        this.semesterStart = semesterStart;
        this.semesterEnd = semesterEnd;
        this.numberOfWeeksInSemester = getNumberOfWeeksBetween();
        initializeAllWeeks();
        initializeCurrentWeekIndex();
    }

    public ClassOfStudents getClassOfStudents()
    {
        return classOfStudents;
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
        String filename = "res/saved-data/saved-lists/weekList.bin";
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
        String filename = "res/saved-data/saved-lists/weekList.bin";

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


    public void exportScheduleAsXML() {
        File file = new File("res/saved-data/schedule.xml");
        PrintWriter out = null;
        try {
            out = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String xml = "";
        xml += "<schedule startTime=\"9:00\" endTime=\"22:00\">";
        for (Week week : this.weekList) {
            xml += "\n" + weekToXML(week);
        }
        xml += "\n</schedule>";

        assert (out != null);
        out.println(xml);
        out.close();
    }

    public void exportWeekAsXML(Week week) {
        File file = new File("res/saved-data/week-schedule.xml");
        PrintWriter out = null;
        try {
            out = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String xml = "";
        xml += "<schedule startTime=\"9:00\" endTime=\"22:00\">";
        xml += weekToXML(week);
        xml += "\n</schedule>";

        assert (out != null);
        out.println(xml);
        out.close();
    }

    private String weekToXML(Week week) {
        String xml = "";
        xml += "<week>";
        for (Day day : week.getDays()) {
            xml += "\n\t<day date=\"" + day.getDate() + "\">";
            for (Lesson lesson : day.getLessons()) {
                xml += "\n\t\t<lesson>";
                xml += "\n\t\t\t<course>" + lesson.getCourse() + "</course>";
                xml += "\n\t\t\t<startTime>" + lesson.getStart() + "</startTime>";
                xml += "\n\t\t\t<endTime>" + lesson.getEnd() + "</endTime>";
                xml += "\n\t\t\t<teacher>" + lesson.getCourse().getTeacherName() + "</teacher>";
                xml += "\n\t\t\t<room>" + (lesson.getRoom() != null ? lesson.getRoom() : "") + (lesson.getRoom2() != null ? " " + lesson.getRoom2() : "") + "</room>";
                xml += "\n\t\t</lesson>";
            }
            xml += "\n\t</day>";
        }
        xml += "\n</week>";

        return xml;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "weekList=" + weekList +
                ", classOfStudents=" + classOfStudents +
                ", numberOfWeeksInSemester=" + numberOfWeeksInSemester +
                ", currentWeekIndex=" + currentWeekIndex +
                ", semesterStart=" + semesterStart +
                ", semesterEnd=" + semesterEnd +
                '}';
    }
}
