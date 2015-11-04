package book_catalogue.query;

import book_catalogue.Book;

public class NumericEqualCondition extends NumericCondition {
    public NumericEqualCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) throws QueryParsingException {
        super(lefthandComponent, righthandComponent);
    }

    @Override
    protected boolean matchesCondition(int diff) {
        return diff == 0;
    }
}

