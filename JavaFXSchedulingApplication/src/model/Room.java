package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Room
{
  private String name;
  private int capacity;
  private String mergeWith;
  private ArrayList<BookingTime> intervals;

  public Room(String name, int capacity, String mergeWith)
  {
    this.name = name;
    this.capacity = capacity;
    this.mergeWith = mergeWith;
    intervals = new ArrayList<>();
  }

  public void Book(BookingTime time)
  {
    intervals.add(time);
  }

  public void unBook(BookingTime time)
  {
    intervals.remove(time);
  }

  public ArrayList<BookingTime> getIntervals()
  {
    return intervals;
  }

  @Override public String toString()
  {
    return "Room{" + "name='" + name + '\'' + ", capacity=" + capacity
        + ", mergeWith='" + mergeWith + '\'' + ", intervals=" + intervals + '}' + "\n";
  }

  public String getName()
  {
    return name;
  }

  public int getCapacity()
  {
    return capacity;
  }

  public String getMergeWith()
  {
    return mergeWith;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setCapacity(int capacity)
  {
    this.capacity = capacity;
  }

  public void setMergeWith(String mergeWith)
  {
    this.mergeWith = mergeWith;
  }

  public void setIntervals(ArrayList<BookingTime> intervals)
  {
    this.intervals = intervals;
  }
}






