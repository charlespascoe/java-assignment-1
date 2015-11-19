package book_catalogue.query;

import book_catalogue.Book;
import java.util.Comparator;

public class BookFieldComparator implements Comparator<Book> {
    public static enum Direction { ASCENDING, DESCENDING }

    private Book.Field field;
    private Direction direction;

    public BookFieldComparator(QueryComponent fieldComponent) throws QueryParsingException {
        if (!(fieldComponent instanceof TextToken)) {
            throw new UnexpectedQueryComponentException(fieldComponent, "text token");
        }

        TextToken fieldToken = (TextToken)fieldComponent;

        if (!Book.Field.isField(fieldToken.getValue())) {
            throw new UnrecognisedFieldException(fieldToken, "field");
        }

        this.field = Book.Field.fromString(fieldToken.getValue());
        this.direction = Direction.ASCENDING;
    }

    public BookFieldComparator(QueryComponent fieldComponent, QueryComponent directionComponent) throws QueryParsingException {
        this(fieldComponent);

        if (!(directionComponent instanceof SpecialToken)) {
            throw new UnexpectedQueryComponentException(directionComponent, "'asc' or 'desc'");
        }

        SpecialToken directionToken = (SpecialToken)directionComponent;

        switch (directionToken.getValue().toLowerCase()) {
            case "asc":
                this.direction = Direction.ASCENDING;
                break;
            case "desc":
                this.direction = Direction.DESCENDING;
                break;
            default:
                throw new UnexpectedQueryComponentException(directionToken, "'asc' or 'desc'");
        }
    }

    public int compare(Book b1, Book b2) {
        int result = b1.compareTo(b2, this.field);

        if (this.direction == Direction.DESCENDING) {
            // If descending, invert result
            result = -result;
        }

        return result;
    }
}

