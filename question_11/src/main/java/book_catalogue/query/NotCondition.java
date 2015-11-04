package book_catalogue.query;

import book_catalogue.Book;

public class NotCondition extends Condition {
    private Condition condition;

    public NotCondition(QueryComponent righthandComponent) throws QueryParsingException {
        // Subtract 4 to include the "not "
        super(righthandComponent.getStartPosition() - 4, righthandComponent.getEndPosition());

        if (!(righthandComponent instanceof Condition)) {
            throw new UnexpectedQueryComponentException(righthandComponent, "condition");
        }

        this.condition = (Condition)righthandComponent;
    }

    @Override
    public boolean isMatch(Book book) {
        return !this.condition.isMatch(book);
    }
}

