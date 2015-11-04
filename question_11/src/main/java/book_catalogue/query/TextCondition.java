package book_catalogue.query;

import book_catalogue.Book;
import book_catalogue.Author;

public abstract class TextCondition extends Condition {
    protected TextToken lefthandToken;
    protected TextToken righthandToken;

    public static final String[] TEXT_FIELDS = new String[] { "title", "author.first_name", "author.second_name", "publisher", "status" };

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

    @Override
    public boolean isMatch(Book book) {
        String inputValue = this.righthandToken.getValue();
        switch (this.lefthandToken.getValue().toLowerCase()) {
            case "title":
                return this.matchesCondition(book.getTitle(), inputValue);
            case "author.first_name":
                for (Author auth : book.getAuthors()) {
                    if (this.matchesCondition(auth.getFirstName(), inputValue)) return true;
                }
                return false;
            case "author.second_name":
                for (Author auth : book.getAuthors()) {
                    if (this.matchesCondition(auth.getSecondName(), inputValue)) return true;
                }
                return false;
            case "publisher":
                return this.matchesCondition(book.getPublisher(), inputValue);
            case "status":
                return this.matchesCondition(book.getStatus().name(), inputValue);
        }

        return false;
    }

    protected abstract boolean matchesCondition(String bookValue, String inputValue);
}

