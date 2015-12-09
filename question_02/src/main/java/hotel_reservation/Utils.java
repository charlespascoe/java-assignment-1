package hotel_reservation;

import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.text.ParseException;

public class Utils {
    private static final SimpleDateFormat DATE_CONVERTER = new SimpleDateFormat("yyyy-MM-dd");

    public static int daysBetweenDates(Calendar start, Calendar end) {
        return (int)TimeUnit.DAYS.convert(end.getTimeInMillis() - start.getTimeInMillis(), TimeUnit.MILLISECONDS);
    }

    public static Calendar date(String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(Utils.DATE_CONVERTER.parse(date));
        return c;
    }

    public static String formatDate(Calendar c) {
        return Utils.DATE_CONVERTER.format(c.getTime());
    }

    public static <T> T[] cloneArray(T[] array) {
        return Arrays.copyOf(array, array.length);
    }
}

