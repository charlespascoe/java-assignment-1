package book_catalogue.query;

import book_catalogue.Book;

public abstract class TextCondition extends Condition {
    protected TextToken lefthandToken;
    protected TextToken righthandToken;

    public TextCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) {
        if (!(lefthandComponent instanceof TextToken)) {
            // Expected text on left hand side
        }

        if (!(righthandComponent instanceof TextToken)) {
            // Expected text on right hand side
        }

        // TODO: Check field value

        this.lefthandToken = (TextToken)lefthandComponent;
        this.righthandToken = (TextToken)righthandComponent;
    }

    protected String getValue(Book book) {
        switch (this.lefthandToken.getValue().toLowerCase()) {
            case "title":
                return book.getTitle();
        }

        return "";
    }
}

