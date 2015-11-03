package book_catalogue.query;

import book_catalogue.Book;

public class NumericLessThanOrEqualCondition extends NumericCondition {
    public NumericLessThanOrEqualCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) {
        super(lefthandComponent, righthandComponent);
    }

    @Override
    public boolean isMatch(Book book) {
        return this.compareTo(book) <= 0;
    }
}

