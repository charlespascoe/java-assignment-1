package book_catalogue.query;

import book_catalogue.Book;

public class LogicalAndCondition extends LogicalCondition {
    public LogicalAndCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) throws QueryParsingException {
        super(lefthandComponent, righthandComponent);
    }

    @Override
    public boolean isMatch(Book book) {
        return this.lefthandCondition.isMatch(book) && this.righthandCondition.isMatch(book);
    }
}

