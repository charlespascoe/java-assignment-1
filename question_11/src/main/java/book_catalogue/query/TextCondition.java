package book_catalogue.query;

import book_catalogue.Book;
import book_catalogue.Author;

public abstract class TextCondition extends Condition {
    protected Book.Field field;
    protected TextToken righthandToken;

    public static final Book.Field[] TEXT_FIELDS = new Book.Field[] { Book.Field.ID, Book.Field.TITLE, Book.Field.AUTHOR_FIRST_NAME, Book.Field.AUTHOR_SECOND_NAME, Book.Field.STATUS };

    public TextCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) throws QueryParsingException {
        super(lefthandComponent.getStartPosition(), righthandComponent.getEndPosition());

        if (!(lefthandComponent instanceof TextToken)) {
            throw new UnexpectedQueryComponentException(lefthandComponent, "text token");
        }

        if (!(righthandComponent instanceof TextToken)) {
            throw new UnexpectedQueryComponentException(righthandComponent, "text token");
        }

        TextToken lefthandToken = (TextToken)lefthandComponent;
        this.righthandToken = (TextToken)righthandComponent;

        if (!TextCondition.isTextField(lefthandToken.getValue())) {
            throw new UnrecognisedFieldException(lefthandToken, "text");
        }

        this.field = Book.Field.fromString(lefthandToken.getValue());
    }

    public static boolean isTextField(String text) {
        for (Book.Field field : TextCondition.TEXT_FIELDS) {
            if (field.toString().equals(text.toLowerCase())) return true;
        }

        return false;
    }

    @Override
    public boolean isMatch(Book book) {
        String inputValue = this.righthandToken.getValue();

        if (this.field == Book.Field.AUTHOR_FIRST_NAME || this.field == Book.Field.AUTHOR_SECOND_NAME) {
            for (String name : (String[])book.getField(this.field)) {
                if (this.matchesCondition(name, inputValue)) return true;
            }

            return false;
        } else {
            return this.matchesCondition((String)book.getField(this.field), inputValue);
        }
    }

    protected abstract boolean matchesCondition(String bookValue, String inputValue);
}

