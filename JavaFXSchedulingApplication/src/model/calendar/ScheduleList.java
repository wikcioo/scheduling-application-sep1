package model.calendar;

import model.courses.ClassOfStudents;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ScheduleList
{
  private ArrayList<Schedule> schedules;
  private Schedule currentSchedule;

  public Schedule getCurrentSchedule()
  {
    return currentSchedule;
  }

  public void setCurrentSchedule(Schedule currentSchedule)
  {
    this.currentSchedule = currentSchedule;
  }

  public ScheduleList() {
    schedules = new ArrayList<>();
    currentSchedule = null;
  }

  public void addSchedule(Schedule schedule) {
    if(getScheduleByClass(schedule.getClassOfStudents(),false) != null) {
      throw new IllegalArgumentException("Class already has a schedule");
    }
    else schedules.add(schedule);
  }

  public Schedule getScheduleByClass(ClassOfStudents class1, boolean createIfNoScheduleFound) {
    for(Schedule schedule : schedules) {
      if(Objects.equals(schedule.getClassOfStudents(),class1))
        return schedule;
    }
    //If schedule doesn't exist for certain class, create a new one schedule

    if(createIfNoScheduleFound) {
      Schedule schedule = new Schedule(LocalDate.of(2021, 8, 30),
          LocalDate.of(2021, 12, 17),
          class1);

      schedules.add(schedule);
      return schedule;
    }
    return null;
  }

  public void saveData() {
    String filename = "res/saved-data/scheduleList.bin";

    ObjectOutputStream out = null;
    try {
      File file = new File(filename);
      FileOutputStream fos = new FileOutputStream(file);
      out = new ObjectOutputStream(fos);
      for(Schedule schedule : this.schedules){
        out.writeObject(schedule);
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

  public void readData() {
    String filename = "res/saved-data/scheduleList.bin";
    ObjectInputStream in = null;
    try {
      File file = new File(filename);
      FileInputStream fis = new FileInputStream(file);
      in = new ObjectInputStream(fis);
      Schedule schedule1;
      int index = 0;
      while((schedule1 = (Schedule) in.readObject())!=null){
        this.schedules.set(index, schedule1);
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


}
