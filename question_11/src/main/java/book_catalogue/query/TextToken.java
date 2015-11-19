package book_catalogue.query;

public class TextToken extends Token {
    public TextToken(int startPos, String value) {
        super(startPos, value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TextToken && super.equals(obj);
    }
}

