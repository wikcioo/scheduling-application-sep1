package model;

import model.rooms.RoomList;

import java.io.File;

public class Test
{
  public static void main(String[] args)
  {

//    LocalDate date1 = LocalDate.of(2021, 4, 3);
//    LocalTime time1  = LocalTime.of(1,0);
//    LocalTime time2 = LocalTime.of(2,0);
//   BookingTime book1 = new BookingTime(date1, time1, time2);
//
//    LocalDate date2 = LocalDate.of(2021, 4, 3);
//    LocalTime time3  = LocalTime.of(3,0);
//    LocalTime time4 = LocalTime.of(4,0);
//    BookingTime book2 = new BookingTime(date2, time3, time4);
//
////    BookingTime book4 = new BookingTime(date1, time1, time2);
////
//  LocalDate date3 = LocalDate.of(2021, 4, 3);
//   LocalTime time5  = LocalTime.of(3,0);
//  LocalTime time6 = LocalTime.of(4,0);
//  BookingTime book3 = new BookingTime(date1, time1, time2);
////
//   Room room1 = new Room("Room1", 40, "Room2");
//  room1.Book(book1);
//  room1.Book(book2);
  //room1.Book(book3);

//   room1.Book(book3);
//   room1.Book(book4);
//    System.out.println(room1.getIntervals().toString());
//       room1.unBook(book4);
//    System.out.println(room1.getIntervals().toString());
    RoomList list = new RoomList();
    File file = new File("res/hod-data/Rooms.txt");
   //list.readRoomsFromTXTFile(file);
  //  list.writeRoomListToBinFile();
    list.readRoomsListFromBinFile();


   // list.writeRoomListToBinFile();
//
  }


}
