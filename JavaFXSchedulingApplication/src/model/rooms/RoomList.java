package model.rooms;

import utilities.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The purpose of this class is to store an arrayList of Room Objects in order
 * to manage them
 */
public class RoomList implements Serializable {
    private ArrayList<Room> rooms;

    /**
     * The purpose of this constructor is to initialize the ArrayList of Rooms
     */
    public RoomList() {
        rooms = new ArrayList<>();
    }

    /**
     * @return a list of all the rooms
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * The purpose of this method is to add a new room to the rooms ArrayList
     * @param room a room object
     */
    public void addRoom(Room room) {
        rooms.add(room);
    }

    /**
     * The purpose of this method is to remove a new room from the rooms ArrayList
     * @param room a room object
     */
    public void removeRoom(Room room) {
        rooms.remove(room);
    }

    /**
     * The purpose of this method is to initialize the rooms ArrayList with an
     * existing ArrayList of rooms
     * @param rooms a list of rooms
     */
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * The purpose of this method is to return all available rooms at a given time
     * interval
     * @param time the time when a room is booked
     * @return returns all available rooms during that time
     */
    public ArrayList<Room> getAvailableRoomsAt(BookingTime time) {
        ArrayList<Room> available = new ArrayList<>();
        for (Room room : rooms)
            if (room.canBeBookedAt(time))
                available.add(room);
        return available;
    }

    /**
     * The purpose of this method is to return all  rooms which have the
     * capacity bigger than the one specified
     * @param minCapacity minimum number of students a room can hold
     * @return all rooms with a capacity bigger or equal to the one specified
     */
    public ArrayList<Room> getRoomsByMinimumCapacity(int minCapacity) {
        ArrayList<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getCapacity() >= minCapacity)
                result.add(room);
        }
        return result;
    }

    /**
     * The purpose of this method is to return all  rooms which can merge
     * with another room
     * @return all rooms which can merge
     */
    public ArrayList<Room> getMergeableRooms() {
        ArrayList<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getMergeWith() != null)
                result.add(room);
        }
        return result;
    }

    /**
     * The purpose of this method is to return all  rooms which can not merge
     * with another room
     * @return all rooms which can not merge
     */
    public ArrayList<Room> getUnMergeableRooms() {
        ArrayList<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getMergeWith() == null)
                result.add(room);
        }
        return result;
    }

    /**
     * The purpose of this method is to return all available rooms which have the
     * capacity bigger than the one specified
     * @param minCapacity minimum number of students a room can hold
     * @param time
     * @return all the rooms which are available and have capacity bigger then the
     * specified one
     */
    public ArrayList<Room> getAvailableRoomsByMinimumCapacity(int minCapacity, BookingTime time) {
        ArrayList<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getCapacity() >= minCapacity && room.canBeBookedAt(time))
                result.add(room);
        }
        return result;
    }

    /**
     * The purpose of this method is to return all available rooms which can merge
     * with another room
     * @param time the time when room is booked
     * @return  all available rooms which can merge
     */
    public ArrayList<Room> getAvailableAndMergeableRooms(BookingTime time) {
        ArrayList<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getMergeWith() != null && room.canBeBookedAt(time))
                result.add(room);
        }
        return result;
    }

    /**
     * The purpose of this method is to return all available rooms which can not merge
     * with another room
     * @param time the time when room is booked
     * @return all available rooms which can not merge
     */
    public ArrayList<Room> getAvailableAndUnMergeableRooms(BookingTime time) {
        ArrayList<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getMergeWith() == null && room.canBeBookedAt(time))
                result.add(room);
        }
        return result;
    }

    /**
     * The purpose of this method is to return all rooms which have the same name
     * as the one specified
     * @param name the name of the room
     * @return a room if it is found by the name or null if it is not
     */
    public Room getRoomByString(String name) {
        for (Room room : rooms) {
            if (room.getName().equals(name))
                return room;
        }
        return null;
    }

    /**
     * The purpose of this method is to read all the rooms' information from a
     * binary file
     */
    public void readRoomsListFromBinFile() {
        String filename = "res/saved-data/saved-lists/roomList.bin";
        ObjectInputStream in = null;
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
            Room room;
            while ((room = (Room) in.readObject()) != null) {
                this.rooms.add(room);
                Logger.info(room.toString());
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The purpose of this method is to read all the rooms' information from a
     * txt file
     * @param file the file we read from, all the courses data
     */
    public void readRoomsFromTXTFile(File file) {
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (in.hasNext()) {
            String roomName = null;
            int capacity = 0;
            String mergeWith = "";
            String line = in.nextLine();
            String[] token = line.split(",");
            try {
                roomName = token[0];
                capacity = Integer.parseInt(token[1]);
                try {
                    mergeWith = token[2];
                } catch (IndexOutOfBoundsException e) {

                }
            } catch (NumberFormatException e) {
                Logger.error("Wrong format, skipping...");
                e.printStackTrace();
            }
            rooms.add(new Room(roomName, capacity, mergeWith));
        }

        in.close();
    }

    /**
     * Returns all the information about the rooms
     * @return room list
     */
    @Override
    public String toString() {
        String str = "roomList: ";
        for (int i = 0; i < rooms.size(); i++) {
            str += rooms.get(i).roomData() + ", ";
        }
        return str;
    }
}
