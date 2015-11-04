package book_catalogue.query;

import book_catalogue.Book;

public class NumericLessThanCondition extends NumericCondition {
    public NumericLessThanCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) throws QueryParsingException {
        super(lefthandComponent, righthandComponent);
    }

    @Override
    public boolean isMatch(Book book) {
        return this.compareTo(book) < 0;
    }
}

