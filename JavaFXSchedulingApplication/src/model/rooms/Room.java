package model.rooms;

import utilities.Logger;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The purpose of this class is to store the information about the room and
 * about the time intervals when the room is booked
 */
public class Room implements Serializable {
    private String name;
    private int capacity;
    private String mergeWith;
    private ArrayList<BookingTime> intervals;

    /**
     * The purpose of this constructor is to initialize all the instance variable
     * of the class
     *
     * @param capacity capacity of the room
     * @param mergeWith the room it can merge with
     * @param name room name
     */
    public Room(String name, int capacity, String mergeWith) {
        this.name = name;
        this.capacity = capacity;
        this.mergeWith = mergeWith;
        intervals = new ArrayList<>();
    }

    /**
     * The purpose of this method is to book a room at a given time interval
     * only if the room is free during that time
     *
     * @param time the time when the room is booked
     */
    public void Book(BookingTime time) {
        if (canBeBookedAt(time))
            intervals.add(time);
        else
            Logger.warn("Room is already booked at that time");
    }

    /**
     * The purpose of this method is to check if a room is free during a given
     * time interval
     * @return true if the room is free, false otherwise
     *
     */
    public boolean canBeBookedAt(BookingTime time) {
        for (BookingTime t : intervals)
            if (t.collideWith(time))
                return false;
        return true;
    }

    /**
     * The purpose of this method is to un-book a room at a given time interval
     * in order to cancel the reservation so that it can be booked again by
     * someone else
     *
     * @param time the time when room is booked
     */
    public void unBook(BookingTime time) {
        intervals.remove(time);
    }

    /**
     * @return a list of all the intervals when the room is booked
     */
    public ArrayList<BookingTime> getIntervals() {
        return intervals;
    }

    /**
     * @return all the information about a room
     */
    public String roomData() {
        return "Room name :" + name + " ,capacity: " + capacity
                + " ,The room can merge with: " + mergeWith + " ,Is booked at" + intervals + "\n";
    }

    /**
     * @return  the name of the room
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * @return the name of the room
     */
    public String getName() {
        return name;
    }

    /**
     * @return the capacity of the room
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @return a string that contains a name of a room, if the room can be merged
     */
    public String getMergeWith() {
        return mergeWith;
    }

    /**
     * The purpose of this method is to set the room's name
     * @param name name of the room
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The purpose of this method is to set the capacity of the room
     * @param capacity the capacity of the room
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * The purpose of this method is to give a room the possibility to merge
     * with another one by giving the other room's name
     * @param mergeWith the possibility of the room to merge with another
     */
    public void setMergeWith(String mergeWith) {
        this.mergeWith = mergeWith;
    }

    /**
     * The purpose of this method is  to appoint a booking time intervals to a room
     * @param intervals the times when the room is booked
     */
    public void setIntervals(ArrayList<BookingTime> intervals) {
        this.intervals = intervals;
    }
}






