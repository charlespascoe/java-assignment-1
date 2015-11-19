package book_catalogue.query;

import book_catalogue.Book;
import book_catalogue.Utils;

public class NumericCondition extends Condition {
    private Book.Field field;
    private SpecialToken conditionToken;
    private NumericToken righthandToken;

    public static final Book.Field[] NUMERIC_FIELDS = new Book.Field[] { Book.Field.PUBLICATION_YEAR };

    public static final SpecialToken.TokenConstant[] NUMERIC_CONDITIONS = new SpecialToken.TokenConstant[] {
        SpecialToken.TokenConstant.EQUALS,
        SpecialToken.TokenConstant.GREATER_THAN,
        SpecialToken.TokenConstant.LESS_THAN,
        SpecialToken.TokenConstant.GREATER_THAN_OR_EQUAL,
        SpecialToken.TokenConstant.LESS_THAN_OR_EQUAL
    };

    public NumericCondition(QueryComponent lefthandComponent, SpecialToken conditionToken, QueryComponent righthandComponent) throws QueryParsingException {
        super(lefthandComponent.getStartPosition(), righthandComponent.getEndPosition());

        if (!NumericCondition.isNumericConditionToken(conditionToken)) {
            throw new UnexpectedQueryComponentException(conditionToken, "numeric condition");
        }

        if (!(lefthandComponent instanceof TextToken)) {
            throw new UnexpectedQueryComponentException(lefthandComponent, "text token");
        }

        if (!(righthandComponent instanceof NumericToken)) {
            throw new UnexpectedQueryComponentException(righthandComponent, "number token");
        }

        TextToken lefthandToken = (TextToken)lefthandComponent;
        this.righthandToken = (NumericToken)righthandComponent;

        if (!NumericCondition.isNumericField(lefthandToken.getValue())) {
            throw new UnrecognisedFieldException(lefthandToken, "numeric");
        }

        this.field = Book.Field.fromString(lefthandToken.getValue());
        this.conditionToken = conditionToken;
    }

    public static boolean isNumericField(String text) {
        for (Book.Field field : NumericCondition.NUMERIC_FIELDS) {
            if (field.toString().equals(text.toLowerCase())) return true;
        }

        return false;
    }

    public static boolean isNumericConditionToken(SpecialToken conditionToken) {
        for (SpecialToken.TokenConstant token : NumericCondition.NUMERIC_CONDITIONS) {
            if (token == conditionToken.getTokenConstant()) return true;
        }
        return false;
    }

    @Override
    public boolean isMatch(Book book) {
        Number inputValue = this.righthandToken.getValue().contains(".") ?
            new Double(this.righthandToken.getDoubleValue()) :
            new Long(this.righthandToken.getIntValue());

        Number bookValue = (Number)book.getField(this.field);

        return this.matchesCondition(Utils.compareNumbers(bookValue, inputValue));
    }

    private boolean matchesCondition(int diff) {
        switch (this.conditionToken.getTokenConstant()) {
            case EQUALS:
                return diff == 0;
            case GREATER_THAN:
                return diff > 0;
            case LESS_THAN:
                return diff < 0;
            case GREATER_THAN_OR_EQUAL:
                return diff >= 0;
            case LESS_THAN_OR_EQUAL:
                return diff <= 0;
            default:
                return false;
        }
    }
}

