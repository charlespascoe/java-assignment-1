package book_catalogue;

import java.util.Calendar;

public class Program {
    public static void main(String[] args) {
        Book test = new Book("The Hitchhiker's Guide to the Galaxy", "Douglas", "Adams", Utils.createCalendar(1979, Calendar.OCTOBER, 12));
        System.out.println(test);
    }
}

