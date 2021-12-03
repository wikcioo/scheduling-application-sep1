package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RoomList
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
        if (!roomName.matches("[A-Za-z ]+")) {
          throw new NumberFormatException();
        }

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
    return "RoomList{" + "rooms=" + rooms + '}';
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
  // export data to binary file
  // import from binary file
  // modify a room field
}
