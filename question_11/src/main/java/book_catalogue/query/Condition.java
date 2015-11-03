package book_catalogue.query;

import book_catalogue.Book;

public abstract class Condition extends QueryComponent {
    public abstract boolean isMatch(Book book);
}

