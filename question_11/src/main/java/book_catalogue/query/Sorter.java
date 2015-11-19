package book_catalogue.query;

import book_catalogue.Book;
import java.util.Comparator;

public class Sorter implements Comparator<Book> {
    private Comparator<Book>[] comparators;

    public Sorter(Comparator<Book>[] comparators) {
        this.comparators = comparators;
    }

    public int compare(Book b1, Book b2) {
        int result = 0;

        for (Comparator<Book> comp : this.comparators) {
            result = comp.compare(b1, b2);
            if (result != 0) return result;
        }

        return 0;
    }

    public boolean equals(Book b1, Book b2) {
        return b1.compareTo(b2) == 0;
    }
}

