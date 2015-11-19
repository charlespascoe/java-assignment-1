package book_catalogue.query;

public class Query {
    private String input;
    private Condition condition;
    private Sorter sorter;

    public Query(String input, Condition condition, Sorter sorter) {
        this.input = input;
        this.condition = condition;
        this.sorter = sorter;
    }

    public Condition getCondition() { return this.condition; }

    public Sorter getSorter() { return this.sorter; }

    @Override
    public String toString() { return this.input; }
}

