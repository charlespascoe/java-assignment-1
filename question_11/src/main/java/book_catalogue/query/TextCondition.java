package book_catalogue.query;

import book_catalogue.Book;

public abstract class TextCondition extends Condition {
    protected TextToken lefthandToken;
    protected TextToken righthandToken;

    public static final String[] TEXT_FIELDS = new String[] { "title" };

    public TextCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) throws QueryParsingException {
        super(lefthandComponent.getStartPosition(), righthandComponent.getEndPosition());

        if (!(lefthandComponent instanceof TextToken)) {
            throw new UnexpectedQueryComponentException(lefthandComponent, "text token");
        }

        if (!(righthandComponent instanceof TextToken)) {
            throw new UnexpectedQueryComponentException(righthandComponent, "text token");
        }

        this.lefthandToken = (TextToken)lefthandComponent;
        this.righthandToken = (TextToken)righthandComponent;

        if (!TextCondition.isTextField(this.lefthandToken.getValue())) {
            throw new UnrecognisedFieldException(this.lefthandToken, "text");
        }
    }

    public static boolean isTextField(String text) {
        for (String field : TextCondition.TEXT_FIELDS) {
            if (field.equals(text.toLowerCase())) return true;
        }

        return false;
    }

    protected String getValue(Book book) {
        switch (this.lefthandToken.getValue().toLowerCase()) {
            case "title":
                return book.getTitle();
        }

        return "";
    }
}

