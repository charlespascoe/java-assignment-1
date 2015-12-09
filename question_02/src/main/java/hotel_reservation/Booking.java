package hotel_reservation;

import java.util.Calendar;

public class Booking {
    private Room room;
    private Calendar startDate;
    private Calendar endDate;
    private int numberOfPeople;

    public Booking(Room room, Calendar startDate, Calendar endDate, int numberOfPeople) {
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfPeople = numberOfPeople;
    }

    public Room getRoom() { return this.room; }

    public Calendar getStartDate() { return (Calendar)this.startDate.clone(); }

    public Calendar geEndDate() { return (Calendar)this.endDate.clone(); }

    public int getNumberOfPeople() { return this.numberOfPeople; }

    public boolean dateCollidesWithBooking(Calendar date) {
        return date.after(this.startDate) && date.before(this.endDate);
    }

    public int getPrice() {
        return Booking.getPrice(this.startDate, this.endDate, this.room.getPricePerNight());
    }

    public static int getPrice(Calendar startDate, Calendar endDate, int pricePerNight) {
        // Number of Days - 1 = Number of Nights
        return (Utils.daysBetweenDates(startDate, endDate) - 1) * pricePerNight;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(Utils.formatDate(this.startDate)).append(" to ").append(Utils.formatDate(this.endDate)).append(", ").append(this.numberOfPeople);

        if (this.numberOfPeople == 1) {
            str.append(" person");
        } else {
            str.append(" people");
        }

        str.append(" (£").append(this.getPrice()).append(")");

        return str.toString();
    }
}

