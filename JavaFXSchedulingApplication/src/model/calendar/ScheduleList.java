package model.calendar;

import model.courses.ClassOfStudents;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ScheduleList implements Serializable {
    private ArrayList<Schedule> schedules;
    private Schedule currentSchedule;

    public Schedule getCurrentSchedule() {
        return currentSchedule;
    }

    public void setCurrentSchedule(Schedule currentSchedule) {
        this.currentSchedule = currentSchedule;
    }

    public ScheduleList() {
        schedules = new ArrayList<>();
        currentSchedule = null;
    }

    public void addSchedule(Schedule schedule) {
        if (getScheduleByClass(schedule.getClassOfStudents(), false) != null) {
            throw new IllegalArgumentException("Class already has a schedule");
        } else schedules.add(schedule);
    }

    public ArrayList<Lesson> getAllLessons() {
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (Schedule s : schedules) {
            for (Week w : s.getWeekList()) {
                for (Day d : w.getDays())
                    lessons.addAll(d.getLessons());
            }
        }
        return lessons;
    }

    public Schedule getScheduleByClass(ClassOfStudents class1, boolean createIfNoScheduleFound) {
        for (Schedule schedule : schedules) {
            if (Objects.equals(schedule.getClassOfStudents(), class1))
                return schedule;
        }
        //If schedule doesn't exist for certain class, create a new one schedule

        if (createIfNoScheduleFound) {
            Schedule schedule = new Schedule(LocalDate.of(2021, 8, 30),
                    LocalDate.of(2021, 12, 17),
                    class1);

            schedules.add(schedule);
            return schedule;
        }
        return null;
    }

    public void writeScheduleListToBinFile(String _file) {
        String filename;
        filename = Objects.requireNonNullElse(_file, "res/saved-data/saved-lists/scheduleList.bin");

        ObjectOutputStream out = null;
        try {
            File file = new File(filename);
            FileOutputStream fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            for (Schedule schedule : this.schedules) {
                out.writeObject(schedule);
                System.out.println(schedule);
                System.out.println("Writing schedule to bin file");
            }
        } catch (IOException e) {
            System.out.println("Exception: " + filename);
        } finally {
            try {
                out.close();
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ScheduleList readScheduleListFromBinFile() {
        String filename = "res/saved-data/saved-lists/scheduleList.bin";
        ObjectInputStream in = null;
        ScheduleList list = null;
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
            Schedule schedule1 = null;

            list = new ScheduleList();
            int index = 0;
            boolean isExist = true;
            while (isExist) {
                if (fis.available() != 0) {
                    try {
                        schedule1 = (Schedule) in.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    this.schedules.set(index++, schedule1);
                    System.out.println("Adding...");
                    System.out.println(schedule1);
                    setCurrentSchedule(schedule1);
                    list.addSchedule(schedule1);
                } else {
                    isExist = false;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found in the resources");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    @Override
    public String toString() {
        return "ScheduleList{" +
                "schedules=" + schedules +
                '}';
    }
}
