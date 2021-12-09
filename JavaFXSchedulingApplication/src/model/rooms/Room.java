package model.rooms;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable
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
    if(canBeBookedAt(time))
    intervals.add(time);
      else
      System.out.println("Room is already booked at that time");
  }

  public boolean canBeBookedAt(BookingTime time)
  {
    for(BookingTime t: intervals)
      if(t.collideWith(time))
        return false;
      return true;
  }

  public void unBook(BookingTime time)
  {
    intervals.remove(time);
  }

  public ArrayList<BookingTime> getIntervals()
  {
    return intervals;
  }

  public String roomData()
  {
    return "Room name :" + name + " ,capacity: " + capacity
        + " ,The room can merge with: " + mergeWith + " ,Is booked at" + intervals + "\n";
  }

  @Override public String toString()
  {
    return name;
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






