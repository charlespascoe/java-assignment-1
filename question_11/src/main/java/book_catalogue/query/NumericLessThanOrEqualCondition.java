package book_catalogue.query;

import book_catalogue.Book;

public class NumericLessThanOrEqualCondition extends NumericCondition {
    public NumericLessThanOrEqualCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) throws QueryParsingException {
        super(lefthandComponent, righthandComponent);
    }

    @Override
    protected boolean matchesCondition(int diff) {
        return diff <= 0;
    }
}

