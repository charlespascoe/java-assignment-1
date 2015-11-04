package book_catalogue.query;

import book_catalogue.Book;

public class TextEqualCondition extends TextCondition {
    public TextEqualCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) throws QueryParsingException {
        super(lefthandComponent, righthandComponent);
    }

    @Override
    public boolean isMatch(Book book) {
        return this.getValue(book).toLowerCase().equals(righthandToken.getValue().toLowerCase());
    }
}

