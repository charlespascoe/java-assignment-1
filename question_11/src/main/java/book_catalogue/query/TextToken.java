package book_catalogue.query;

public class TextToken extends Token {
    private String value;

    public TextToken(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

