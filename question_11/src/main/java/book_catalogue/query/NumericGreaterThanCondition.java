package book_catalogue.query;

import book_catalogue.Book;

public class NumericGreaterThanCondition extends NumericCondition {
    public NumericGreaterThanCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) {
        super(lefthandComponent, righthandComponent);
    }

    @Override
    public boolean isMatch(Book book) {
        return this.compareTo(book) > 0;
    }
}

