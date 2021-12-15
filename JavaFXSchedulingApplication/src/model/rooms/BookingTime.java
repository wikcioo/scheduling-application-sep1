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
     * @param date day of reservation
     * @param bookingEnd start of the reservation
     * @param bookingStart end of the reservation
     */
    public BookingTime(LocalDate date, LocalTime bookingStart, LocalTime bookingEnd) {
        this.date = date;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
    }

    /**
     * The purpose of this method is to compare two BookingTime objects and returns
     * true if bookingStart, bookingEnd and date are the same and false otherwise
     * @param o the object that is being compared to the BookingTime object
     * @return true if BookingTime objects are the same, false otherwise
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
     * @return all information about booking time
     */
    @Override
    public String toString() {
        return "BookingTime{" + "bookingStart=" + bookingStart + ", bookingEnd="
                + bookingEnd + ", date=" + date + '}';
    }

    /**
     * @return  the start time of the booking
     */
    public LocalTime getBookingStart() {
        return bookingStart;
    }

    /**
     * @return  the end time of the booking
     */
    public LocalTime getBookingEnd() {
        return bookingEnd;
    }

    /**
     * @return  the date of the booking
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * The purpose of this method is to set a booking start time
     * @param bookingStart the start of booking
     */
    public void setBookingStart(LocalTime bookingStart) {
        this.bookingStart = bookingStart;
    }

    /**
     * The purpose of this method is to set a booking end time
     * @param bookingEnd the end of booking
     */
    public void setBookingEnd(LocalTime bookingEnd) {
        this.bookingEnd = bookingEnd;
    }

    /**
     * The purpose of this method is to set the day when a room is booked
     * @param date the day of booking
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * The purpose of this method is to check if it is possible to book a
     * room in a given time interval
     *
     * @param time the time when a room is booked
     * @return true if they do not collide, false otherwise
     */
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
