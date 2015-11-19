package book_catalogue.query;

import book_catalogue.Book;

public class NotCondition extends Condition {
    private Condition condition;

    public NotCondition(SpecialToken notToken, QueryComponent righthandComponent) throws QueryParsingException {
        super(notToken.getStartPosition(), righthandComponent.getEndPosition());

        if (notToken.getTokenConstant() != SpecialToken.TokenConstant.NOT) {
            throw new UnexpectedQueryComponentException(notToken, "'not'");
        }

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

