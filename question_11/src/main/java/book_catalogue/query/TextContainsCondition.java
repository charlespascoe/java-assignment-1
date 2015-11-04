package book_catalogue.query;

public class TextContainsCondition extends TextCondition {
    public TextContainsCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) throws QueryParsingException {
        super(lefthandComponent, righthandComponent);
    }

    @Override
    protected boolean matchesCondition(String bookValue, String inputValue) {
        return bookValue.toLowerCase().contains(inputValue.toLowerCase());
    }
}

