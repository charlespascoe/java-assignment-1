package book_catalogue.query;

import book_catalogue.Book;

public abstract class NumericCondition extends Condition {
    private TextToken lefthandToken;
    private NumericToken righthandToken;

    public static final String[] NUMERIC_FIELDS = new String[] { "publication_year" };

    public NumericCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) throws QueryParsingException {
        super(lefthandComponent.getStartPosition(), righthandComponent.getEndPosition());

        if (!(lefthandComponent instanceof TextToken)) {
            throw new UnexpectedQueryComponentException(lefthandComponent, "text token");
        }

        if (!(righthandComponent instanceof NumericToken)) {
            throw new UnexpectedQueryComponentException(righthandComponent, "number token");
        }

        this.lefthandToken = (TextToken)lefthandComponent;
        this.righthandToken = (NumericToken)righthandComponent;

        if (!NumericCondition.isNumericField(this.lefthandToken.getValue())) {
            throw new UnrecognisedFieldException(this.lefthandToken, "numeric");
        }
    }

    public static boolean isNumericField(String text) {
        for (String field : NumericCondition.NUMERIC_FIELDS) {
            if (field.equals(text.toLowerCase())) return true;
        }

        return false;
    }

    @Override
    public boolean isMatch(Book book) {
        switch (this.lefthandToken.getValue().toLowerCase()) {
            case "publication_year":
                int bookValue = book.getPublicationYear();
                int inputValue = this.righthandToken.getIntValue();

                return this.matchesCondition(bookValue - inputValue);
        }

        return false;
    }

    protected abstract boolean matchesCondition(int diff);
}

