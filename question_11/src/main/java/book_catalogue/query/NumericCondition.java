package book_catalogue.query;

import book_catalogue.Book;

public abstract class NumericCondition extends Condition {
    private TextToken lefthandToken;
    private NumericToken righthandToken;

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

    public static boolean isNumericField(String field) {
        return field.equals("publication_year");
    }

    protected int compareTo(Book book) {
        switch (this.lefthandToken.getValue().toLowerCase()) {
            case "publication_year":
                int bookValue = book.getPublicationYear();
                int inputValue = this.righthandToken.getIntValue();

                return bookValue - inputValue;
        }

        return 0;
    }

}

