package hotel_reservation;

import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class Utils {
    private static final SimpleDateFormat DATE_CONVERTER;

    static {
        Utils.DATE_CONVERTER = new SimpleDateFormat("YYYY-MM-dd");
    }

    public static long daysBetweenDates(Calendar start, Calendar end) {
        return TimeUnit.DAYS.convert(end.getTimeInMillis() - start.getTimeInMillis(), TimeUnit.MILLISECONDS);
    }

    public static Calendar date(String date) {
        Calendar c = Calendar.getInstance();
        c.setTime(Utils.DATE_CONVERTER.parse(date));
        return c;
    }

    public static <T> T[] cloneArray(T[] array) {
        return Arrays.copyOf(array, array.length);
    }
}

