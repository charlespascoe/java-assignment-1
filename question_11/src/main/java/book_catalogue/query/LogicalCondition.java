package book_catalogue.query;

public abstract class LogicalCondition extends Condition {
    protected Condition lefthandCondition;
    protected Condition righthandCondition;

    public LogicalCondition(QueryComponent lefthandComponent, QueryComponent righthandComponent) {
        if (!(lefthandComponent instanceof Condition)) {
            // ### Unexpected component type
        }

        if (!(righthandComponent instanceof Condition)) {
            // ### Unexpected component type
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
