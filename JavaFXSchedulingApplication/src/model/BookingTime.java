package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class BookingTime implements Serializable
{
  private LocalTime bookingStart;
  private LocalTime bookingEnd;
  private LocalDate date;

  public BookingTime(LocalDate date ,LocalTime bookingStart, LocalTime bookingEnd)
  {
    this.date = date;
    this.bookingStart = bookingStart;
    this.bookingEnd = bookingEnd;
  }

  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    BookingTime that = (BookingTime) o;
    return Objects.equals(bookingStart, that.bookingStart) && Objects.equals(
        bookingEnd, that.bookingEnd) && Objects.equals(date, that.date);
  }

  @Override public int hashCode()
  {
    return Objects.hash(bookingStart, bookingEnd, date);
  }

  @Override public String toString()
  {
    return "BookingTime{" + "bookingStart=" + bookingStart + ", bookingEnd="
        + bookingEnd + ", date=" + date + '}';
  }

  public LocalTime getBookingStart()
  {
    return bookingStart;
  }

  public LocalTime getBookingEnd()
  {
    return bookingEnd;
  }

  public LocalDate getDate()
  {
    return date;
  }

  public void setBookingStart(LocalTime bookingStart)
  {
    this.bookingStart = bookingStart;
  }

  public void setBookingEnd(LocalTime bookingEnd)
  {
    this.bookingEnd = bookingEnd;
  }

  public void setDate(LocalDate date)
  {
    this.date = date;
  }
  // compare time intervals

  public boolean collideWith(BookingTime time)
  {
    if(!(date.equals(time.getDate())))
      return false;
    if(time.bookingEnd.isBefore(bookingStart))
      return false;
    if(bookingEnd.isBefore(time.bookingStart))
      return false;
    return true;
  }

}
