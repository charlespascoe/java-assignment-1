package book_catalogue.query;

public class NotCondition extends Condition {
    private Condtion condition;

    public NotCondition(Token righthandToken) {
        if (!(righthandToken instanceof Condtion)) {
            // ### Unexpected token type
        }

        this.condition = (Condtion)righthandToken;
    }

    @Override
    public boolean isMatch(Book book) {
        return !this.condition.isMatch(book);
    }
}

