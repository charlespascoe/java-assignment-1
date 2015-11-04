package book_catalogue.query;

import book_catalogue.Book;

public abstract class Condition extends QueryComponent {
    public Condition(int startPos, int endPos) {
        super(startPos, endPos);
    }

    public abstract boolean isMatch(Book book);
}

