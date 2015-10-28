package book_catalogue;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Utils {
    public static Calendar createCalendar(int year, int month, int dayOfMonth) {
        Calendar c = new GregorianCalendar();
        c.set(year, month, dayOfMonth);
        return c;
    }
}

