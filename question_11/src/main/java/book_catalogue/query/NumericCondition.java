package book_catalogue.query;

import book_catalogue.Book;
import book_catalogue.Utils;

public abstract class NumericCondition extends Condition {
    private Book.Field field;
    private NumericToken righthandToken;

    public static final Book.Field[] NUMERIC_FIELDS = new Book.Field[] { Book.Field.PUBLICATION_YEAR };

    public NumericCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) throws QueryParsingException {
        super(lefthandComponent.getStartPosition(), righthandComponent.getEndPosition());

        if (!(lefthandComponent instanceof TextToken)) {
            throw new UnexpectedQueryComponentException(lefthandComponent, "text token");
        }

        if (!(righthandComponent instanceof NumericToken)) {
            throw new UnexpectedQueryComponentException(righthandComponent, "number token");
        }

        TextToken lefthandToken = (TextToken)lefthandComponent;
        this.righthandToken = (NumericToken)righthandComponent;

        if (!NumericCondition.isNumericField(lefthandToken.getValue())) {
            throw new UnrecognisedFieldException(lefthandToken, "numeric");
        }

        this.field = Book.Field.fromString(lefthandToken.getValue());
    }

    public static boolean isNumericField(String text) {
        for (Book.Field field : NumericCondition.NUMERIC_FIELDS) {
            if (field.toString().equals(text.toLowerCase())) return true;
        }

        return false;
    }

    @Override
    public boolean isMatch(Book book) {
        Number inputValue = this.righthandToken.getValue().contains(".") ?
            new Double(this.righthandToken.getDoubleValue()) :
            new Long(this.righthandToken.getIntValue());

        Number bookValue = (Number)book.getField(this.field);

        return this.matchesCondition(Utils.compareNumbers(bookValue, inputValue));
    }

    protected abstract boolean matchesCondition(int diff);
}

