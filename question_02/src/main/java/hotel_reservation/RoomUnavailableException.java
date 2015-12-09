package hotel_reservation;

import java.util.Calendar;

public class RoomUnavailableException extends Exception {
    public RoomUnavailableException(int number, Calendar startDate, Calendar endDate, int people) {
        super(String.format("Room #%s is not available between %s and %s for %s " + (people == 1 ? "person" : "people"), number, Utils.formatDate(startDate), Utils.formatDate(endDate), people));
    }
}
