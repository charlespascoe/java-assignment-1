package book_catalogue.query;

import book_catalogue.Book;

public class TextContainsCondition extends TextCondition {
    public TextContainsCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) {
        super(lefthandComponent, righthandComponent);
    }

    @Override
    public boolean isMatch(Book book) {
        return this.getValue(book).toLowerCase().contains(righthandToken.getValue().toLowerCase());
    }
}

