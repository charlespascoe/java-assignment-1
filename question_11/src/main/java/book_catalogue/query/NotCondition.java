package book_catalogue.query;

import book_catalogue.Book;

public class NotCondition extends Condition {
    private Condition condition;

    public NotCondition(QueryComponent righthandComponent) {
        if (!(righthandComponent instanceof Condition)) {
            // ### Unexpected component type
        }

        this.condition = (Condition)righthandComponent;
    }

    @Override
    public boolean isMatch(Book book) {
        return !this.condition.isMatch(book);
    }
}

