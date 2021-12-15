package model.rooms;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * The purpose of this class is to store information about a booking period
 */
public class BookingTime implements Serializable {
    private LocalTime bookingStart;
    private LocalTime bookingEnd;
    private LocalDate date;

    /**
     * The purpose of this constructor is to initialize all the instance variables of this class
     */
    public BookingTime(LocalDate date, LocalTime bookingStart, LocalTime bookingEnd) {
        this.date = date;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
    }

    /**
     * The purpose of this method is to compare two BookingTime objects and returns
     * true if bookingStart, bookingEnd and date are the same and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BookingTime that = (BookingTime) o;
        return Objects.equals(bookingStart, that.bookingStart) && Objects.equals(
                bookingEnd, that.bookingEnd) && Objects.equals(date, that.date);
    }

    /**
     * The purpose of this method is to return all the information about the
     * booking time
     */
    @Override
    public String toString() {
        return "BookingTime{" + "bookingStart=" + bookingStart + ", bookingEnd="
                + bookingEnd + ", date=" + date + '}';
    }

    /**
     * Returns the start time of the booking
     */
    public LocalTime getBookingStart() {
        return bookingStart;
    }

    /**
     * Returns the end time of the booking
     */
    public LocalTime getBookingEnd() {
        return bookingEnd;
    }

    /**
     * Returns the date of the booking
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * The purpose of this method is to set a booking start time
     */
    public void setBookingStart(LocalTime bookingStart) {
        this.bookingStart = bookingStart;
    }

    /**
     * The purpose of this method is to set a booking end time
     */
    public void setBookingEnd(LocalTime bookingEnd) {
        this.bookingEnd = bookingEnd;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean collideWith(BookingTime time) {
        if (!(date.equals(time.getDate())))
            return false;
        if (time.bookingEnd.isBefore(bookingStart))
            return false;
        if (bookingEnd.isBefore(time.bookingStart))
            return false;
        return true;
    }
}
