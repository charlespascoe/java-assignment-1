package book_catalogue.query;

import book_catalogue.Book;

public abstract class NumericCondition extends Condition {
    private TextToken lefthandToken;
    private NumericToken righthandToken;

    public NumericCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) {
        if (!(lefthandComponent instanceof TextToken)) {
            // ### Unexpected component type!
        }

        if (!(righthandComponent instanceof NumericToken)) {
            // ### Unexpected component type!
        }

        // TODO: Add check for field

        this.lefthandToken = (TextToken)lefthandComponent;
        this.righthandToken = (NumericToken)righthandComponent;
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

