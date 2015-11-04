package book_catalogue.query;

public class QueryParsingException extends Exception {
    private int startPos;
    private int endPos;

    public QueryParsingException(int pos, String message) {
        this(pos, pos, message);
    }

    public QueryParsingException(int startPos, int endPos, String message) {
        super(message);
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public int getStartPosition() {
        return this.startPos;
    }

    public int getEndPosition() {
        return this.endPos;
    }
}

