package book_catalogue.query;

import book_catalogue.Book;
import book_catalogue.Matcher;

public abstract class Condition extends QueryComponent implements Matcher<Book> {
    public Condition(int startPos, int endPos) {
        super(startPos, endPos);
    }

    public abstract boolean isMatch(Book book);
}

