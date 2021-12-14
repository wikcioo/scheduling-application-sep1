package model.rooms;

import utilities.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RoomList implements Serializable {
    private ArrayList<Room> rooms;

    public RoomList() {
        rooms = new ArrayList<>();
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<Room> getAvailableRoomsAt(BookingTime time) {
        ArrayList<Room> available = new ArrayList<>();
        for (Room room : rooms)
            if (room.canBeBookedAt(time))
                available.add(room);
        return available;
    }

    public ArrayList<Room> getRoomsByMinimumCapacity(int minCapacity) {
        ArrayList<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getCapacity() >= minCapacity)
                result.add(room);
        }
        return result;
    }

    public ArrayList<Room> getMergeableRooms() {
        ArrayList<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getMergeWith() != null)
                result.add(room);
        }
        return result;
    }

    public ArrayList<Room> getUnMergeableRooms() {
        ArrayList<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getMergeWith() == null)
                result.add(room);
        }
        return result;
    }

    public ArrayList<Room> getAvailableRoomsByMinimumCapacity(int minCapacity, BookingTime time) {
        ArrayList<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getCapacity() >= minCapacity && room.canBeBookedAt(time))
                result.add(room);
        }
        return result;
    }

    public ArrayList<Room> getAvailableAndMergeableRooms(BookingTime time) {
        ArrayList<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getMergeWith() != null && room.canBeBookedAt(time))
                result.add(room);
        }
        return result;
    }

    public ArrayList<Room> getAvailableAndUnMergeableRooms(BookingTime time) {
        ArrayList<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getMergeWith() == null && room.canBeBookedAt(time))
                result.add(room);
        }
        return result;
    }

    public Room getRoomByString(String name) {
        for (Room room : rooms) {
            if (room.getName().equals(name))
                return room;
        }
        return null;
    }

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

    @Override
    public String toString() {
        String str = "roomList: ";
        for (int i = 0; i < rooms.size(); i++) {
            str += rooms.get(i).roomData() + ", ";
        }
        return str;
    }
}
