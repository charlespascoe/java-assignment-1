package hotel_reservation;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public class Room {
    private int number;
    private int numberOfBeds;
    private String[] features;
    private int pricePerNight;
    private List<Booking> bookings;

    public Room(int number, int numberOfBeds, int pricePerNight, String... features) {
        this.number = number;
        this.numberOfBeds = numberOfBeds;
        this.features = Utils.cloneArray(features);
        this.pricePerNight = pricePerNight;

        this.bookings = new ArrayList<Booking>();
    }

    public int getNumber() { return this.number; }

    public int getNumberOfBeds() { return this.numberOfBeds; }

    public String[] getFeatures() { return Utils.cloneArray(this.features); }

    public int getPricePerNight() { return this.pricePerNight; }

    public Booking bookRoom(Calendar startDate, Calendar endDate, int people) throws IllegalArgumentException, RoomUnavailableException {
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start Date must be before End Date");
        }

        if (people < 1) {
            throw new IllegalArgumentException("There must be at least 1 person");
        }

        if (!this.isAvailable(startDate, endDate) || this.numberOfBeds < people) {
            throw new RoomUnavailableException(this.number, startDate, endDate, people);
        }

        Booking booking = new Booking(this, startDate, endDate, people);
        this.bookings.add(booking);
        return booking;
    }

    public void removeBooking(Booking booking) {
        if (this.bookings.contains(booking)) {
            this.bookings.remove(booking);
        }
    }

    public boolean isAvailable(Calendar startDate, Calendar endDate) {
        for (Booking booking : this.bookings) {
            if (booking.dateCollidesWithBooking(startDate) || booking.dateCollidesWithBooking(endDate)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(String.format("Room Number: %s%n", this.number));
        str.append("Number of beds: ").append(this.numberOfBeds).append("\n");

        if (this.features.length > 0) {
            str.append("Features:\n");

            for (String feature : this.features) {
                str.append("    ").append(feature).append("\n");
            }
        }

        str.append("Bookings:\n");

        for (Booking booking : this.bookings) {
            str.append("    ").append(booking.toString()).append("\n");
        }

        return str.toString();
    }
}

