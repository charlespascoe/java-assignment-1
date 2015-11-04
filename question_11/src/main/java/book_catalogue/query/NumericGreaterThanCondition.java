package book_catalogue.query;

import book_catalogue.Book;

public class NumericGreaterThanCondition extends NumericCondition {
    public NumericGreaterThanCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) throws QueryParsingException {
        super(lefthandComponent, righthandComponent);
    }

    @Override
    protected boolean matchesCondition(int diff) {
        return diff > 0;
    }
}

