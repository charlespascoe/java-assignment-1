package book_catalogue.query;

public class UnexpectedQueryComponentException extends QueryParsingException {
    private QueryComponent unexpectedComponent;

    public UnexpectedQueryComponentException(QueryComponent unexpectedComponent, String expectedComponentName) {
        super(
            unexpectedComponent.getStartPosition(),
            unexpectedComponent.getEndPosition(),
            "Unexpected component: expecting a " + expectedComponentName + ", got a " + unexpectedComponent.toString()
        );

        this.unexpectedComponent = unexpectedComponent;
    }
}

