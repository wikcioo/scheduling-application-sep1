package model;

import javafx.util.converter.LocalTimeStringConverter;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;

public class Test
{
  public static void main(String[] args)
  {

//    LocalDate date1 = LocalDate.of(2021, 4, 3);
//    LocalTime time1  = LocalTime.of(1,1);
//    LocalTime time2 = LocalTime.of(2,1);
//   BookingTime book1 = new BookingTime(date1, time1, time2);
//
//    LocalDate date2 = LocalDate.of(2021, 4, 3);
//    LocalTime time3  = LocalTime.of(3,1);
//    LocalTime time4 = LocalTime.of(4,1);
//    BookingTime book2 = new BookingTime(date1, time1, time2);
//    BookingTime book4 = new BookingTime(date1, time1, time2);
//
//    LocalDate date3 = LocalDate.of(2021, 4, 3);
//    LocalTime time5  = LocalTime.of(2,1);
//    LocalTime time6 = LocalTime.of(3,1);
//    BookingTime book3 = new BookingTime(date1, time1, time2);
//
//   Room room1 = new Room("Room1", 40, "Room2");
//   room1.Book(book1);
//   room1.Book(book2);
//   room1.Book(book3);
//   room1.Book(book4);
//    System.out.println(room1.getIntervals().toString());
//       room1.unBook(book4);
//    System.out.println(room1.getIntervals().toString());
    RoomList list = new RoomList();
    File file = new File("res/hod-data/Rooms.txt");
    list.readRoomsFromTXTFile(file);
    System.out.println(list.getRooms().toString());
  }


}
