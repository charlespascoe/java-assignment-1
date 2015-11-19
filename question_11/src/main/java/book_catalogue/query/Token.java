package book_catalogue.query;

public abstract class Token extends QueryComponent {
    private String value;

    public Token(int startPos, String value) {
        super(startPos, startPos + value.length() - 1);
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Token) {
            return ((Token)obj).getValue().equalsIgnoreCase(this.getValue());
        } else {
            return false;
        }
    }
}

