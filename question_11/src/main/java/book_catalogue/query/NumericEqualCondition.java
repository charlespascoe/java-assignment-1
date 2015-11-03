package book_catalogue.query;

import book_catalogue.Book;

public class NumericEqualCondition extends NumericCondition {
    public NumericEqualCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) {
        super(lefthandComponent, righthandComponent);
    }

    @Override
    public boolean isMatch(Book book) {
        return this.compareTo(book) == 0;
    }
}

