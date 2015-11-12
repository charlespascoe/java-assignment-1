package hotel_reservation;

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
        return this.startDate.after(date) && this.endDate.before(date);
    }

    public int getPrice() {
        // Number of Days - 1 = Number of Nights
        return (Utils.daysBetweenDates(this.startDate, this.endDate) - 1) * this.room.getPricePerNight();
    }
}

