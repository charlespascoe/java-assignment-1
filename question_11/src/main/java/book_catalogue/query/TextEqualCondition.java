package book_catalogue.query;

public class TextEqualCondition extends TextCondition {
    public TextEqualCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) throws QueryParsingException {
        super(lefthandComponent, righthandComponent);
    }

    @Override
    protected boolean matchesCondition(String bookValue, String inputValue) {
        return bookValue.toLowerCase().equals(inputValue.toLowerCase());
    }
}

