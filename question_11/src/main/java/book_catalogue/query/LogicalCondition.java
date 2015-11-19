package book_catalogue.query;

import book_catalogue.Book;

public class LogicalCondition extends Condition {
    private Condition lefthandCondition;
    private SpecialToken logicalConditionToken;
    private Condition righthandCondition;

    public static final SpecialToken.TokenConstant[] LOGICAL_CONDITIONS = new SpecialToken.TokenConstant[] {
        SpecialToken.TokenConstant.AND,
        SpecialToken.TokenConstant.OR
    };

    public LogicalCondition(QueryComponent lefthandComponent, SpecialToken logicalConditionToken, QueryComponent righthandComponent) throws QueryParsingException {
        super(lefthandComponent.getStartPosition(), righthandComponent.getEndPosition());

        if (!LogicalCondition.isLogicalConditionToken(logicalConditionToken)) {
            throw new UnexpectedQueryComponentException(logicalConditionToken, "logical condition");
        }

        if (!(lefthandComponent instanceof Condition)) {
            throw new UnexpectedQueryComponentException(lefthandComponent, "condition");
        }

        if (!(righthandComponent instanceof Condition)) {
            throw new UnexpectedQueryComponentException(righthandComponent, "condition");
        }

        this.lefthandCondition = (Condition)lefthandComponent;
        this.logicalConditionToken = logicalConditionToken;
        this.righthandCondition = (Condition)righthandComponent;
    }

    public static boolean isLogicalConditionToken(SpecialToken logicalConditionToken) {
        for (SpecialToken.TokenConstant token : LogicalCondition.LOGICAL_CONDITIONS) {
            if (token == logicalConditionToken.getTokenConstant()) return true;
        }
        return false;
    }

    @Override
    public boolean isMatch(Book book) {
        switch (this.logicalConditionToken.getTokenConstant()) {
            case AND:
                return this.lefthandCondition.isMatch(book) && this.righthandCondition.isMatch(book);
            case OR:
                return this.lefthandCondition.isMatch(book) || this.righthandCondition.isMatch(book);
            default:
                return false;
        }
    }
}
