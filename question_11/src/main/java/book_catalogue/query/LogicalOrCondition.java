package book_catalogue.query;

import book_catalogue.Book;

public class LogicalOrCondition extends LogicalCondition {
    public LogicalOrCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) {
        super(lefthandComponent, righthandComponent);
    }

    @Override
    public boolean isMatch(Book book) {
        return this.lefthandCondition.isMatch(book) || this.righthandCondition.isMatch(book);
    }
}

