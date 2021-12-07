package model.rooms;

import model.rooms.BookingTime;
import model.rooms.Room;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RoomList implements Serializable
{
  private ArrayList<Room> rooms ;

  public RoomList()
  {
    rooms = new ArrayList<>();
  }


  public  void readRoomsFromTXTFile(File file) {

    Scanner in = null;
    try {
      in = new Scanner(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    while (in.hasNext()){
      String roomName = null;
      int capacity = 0;
      String mergeWith = null;
      String line = in.nextLine();
      String[] token = line.split(",");
      try {
        roomName = token[0];
        capacity = Integer.parseInt(token[1]);
        try
        {
         mergeWith = token[2];
        } catch (IndexOutOfBoundsException e)
        {}
//        if (!roomName.matches("[A-Za-z ]+")) {
//          throw new NumberFormatException();
//        }

      } catch (NumberFormatException e) {
        System.out.println("Wrong format, skipping...");
        e.printStackTrace();
    }
      rooms.add(new Room(roomName, capacity, mergeWith));

  }
    in.close();
}

  @Override public String toString()
  {
    return "Room name: " + rooms;
  }

  public ArrayList<Room> getRooms()
  {
    return rooms;
  }

  public void addRoom(Room room)
  {
    rooms.add(room);
  }

  public void removeRoom(Room room)
  {
    rooms.remove(room);
  }

  public void setRooms(ArrayList<Room> rooms)
  {
    this.rooms = rooms;
  }

  // display available ones
  public ArrayList<Room> getAvailableRoomsAt(BookingTime time)
  {
    ArrayList<Room> available = new ArrayList<>();
    for(Room room : rooms)
      if(room.canBeBookedAt(time))
        available.add(room);
      return available;
  }

  public void writeRoomListToBinFile() {
    String filename = "res/saved-data/RoomList.bin";

    ObjectOutputStream out = null;
    try {
      File file = new File(filename);
      FileOutputStream fos = new FileOutputStream(file);
      out = new ObjectOutputStream(fos);
      for(Room room : this.rooms){
        out.writeObject(room);
      }
    } catch (IOException e) {
      System.out.println("Exception: " + filename);
      e.printStackTrace();
    } finally {
      try {
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void readRoomsListFromBinFile() {
    String filename = "res/saved-data/RoomList.bin";
    ObjectInputStream in = null;
    try {
      File file = new File(filename);
      FileInputStream fis = new FileInputStream(file);
      in = new ObjectInputStream(fis);
      Room room;
      while((room = (Room) in.readObject()) != null){
        this.rooms.add(room);
        System.out.println(room);
      }

    } catch (IOException | ClassNotFoundException e) {
      //e.printStackTrace();
    } finally {
      try {
        in.close();
      } catch (IOException e) {
        //e.printStackTrace();
      }
    }
  }

  public ArrayList<Room> getRoomsByMinimumCapacity(int minCapacity)//manage
  {
    ArrayList<Room> result = new ArrayList<>();
    for(Room room: rooms)
    {
      if(room.getCapacity() >= minCapacity)
        result.add(room);
    }
    return result;
  }

  public ArrayList<Room> getMergeableRooms()//manage
  {
    ArrayList<Room> result = new ArrayList<>();
    for(Room room: rooms)
    {
      if(room.getMergeWith()!=null)
        result.add(room);
    }
    return result;
  }

  public ArrayList<Room> getUnMergeableRooms()//manage
  {
    ArrayList<Room> result = new ArrayList<>();
    for(Room room: rooms)
    {
      if(room.getMergeWith()==null)
        result.add(room);
    }
    return result;
  }

  public ArrayList<Room> getAvailableRoomsByMinimumCapacity(int minCapacity, BookingTime time)//book
  {
    ArrayList<Room> result = new ArrayList<>();
    for(Room room: rooms)
    {
      if(room.getCapacity() >= minCapacity && room.canBeBookedAt(time))
        result.add(room);
    }
    return result;
  }

  public ArrayList<Room> getAvailableAndMergeableRooms(BookingTime time)//book
  {
    ArrayList<Room> result = new ArrayList<>();
    for(Room room: rooms)
    {
      if(room.getMergeWith()!=null && room.canBeBookedAt(time))
        result.add(room);
    }
    return result;
  }

  public ArrayList<Room> getAvailableAndUnMergeableRooms(BookingTime time)//book
  {
    ArrayList<Room> result = new ArrayList<>();
    for(Room room: rooms)
    {
      if(room.getMergeWith()==null && room.canBeBookedAt(time))
        result.add(room);
    }
    return result;
  }






  // modify a room field
}