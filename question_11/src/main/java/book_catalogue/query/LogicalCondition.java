package book_catalogue.query;

public abstract class LogicalCondition extends Condition {
    protected Condition lefthandCondition;
    protected Condition righthandCondition;

    public LogicalCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) throws QueryParsingException {
        super(lefthandComponent.getStartPosition(), righthandComponent.getEndPosition());

        if (!(lefthandComponent instanceof Condition)) {
            throw new UnexpectedQueryComponentException(lefthandComponent, "condition");
        }

        if (!(righthandComponent instanceof Condition)) {
            throw new UnexpectedQueryComponentException(righthandComponent, "condition");
        }

        this.lefthandCondition = (Condition)lefthandComponent;
        this.righthandCondition = (Condition)righthandComponent;
    }

    public static boolean isLogicalCondition(String token) {
        return
            token.toLowerCase().equals("and") ||
            token.toLowerCase().equals("or");
    }
}
