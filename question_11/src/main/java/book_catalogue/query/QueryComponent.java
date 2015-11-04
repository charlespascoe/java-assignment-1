package book_catalogue.query;

public abstract class QueryComponent {
    private int startPos;
    private int endPos;

    public QueryComponent(int startPos, int endPos) {
        this.startPos = startPos;

        if (endPos < startPos) endPos = startPos;

        this.endPos = endPos;
    }

    public int getStartPosition() {
        return this.startPos;
    }

    public int getEndPosition() {
        return this.endPos;
    }
}
