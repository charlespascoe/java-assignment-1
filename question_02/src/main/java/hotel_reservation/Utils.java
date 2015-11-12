package hotel_reservation;

import java.util.concurrent.TimeUnit;

public class Utils {
    public static long daysBetweenDates(Calendar start, Calendar end) {
        return TimeUnit.DAYS.convert(end.getTimeInMillis() - start.getTimeInMillis(), TimeUnit.MILLISECONDS);
    }
}

