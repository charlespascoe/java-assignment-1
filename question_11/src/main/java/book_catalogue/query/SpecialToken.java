package book_catalogue.query;

public class SpecialToken extends Token {
    public static final String[] SPECIAL_TOKENS = new String[] { "(", ")", "==", "~~", ">", "<", ">=", "<=", "and", "or", "not", "sort", "asc", "desc", "," };

    public SpecialToken(String value) throws QueryParsingException {
        this(0, value);
    }

    public SpecialToken(int startPos, String value) throws QueryParsingException {
        super(startPos, value);

        if (!SpecialToken.isSpecialToken(value)) {
            throw new QueryParsingException(
                this.getStartPosition(),
                this.getEndPosition(),
                "Not a valid special token: " + value
            );
        }
    }

    public static boolean isSpecialToken(String value) {
        for (String st : SpecialToken.SPECIAL_TOKENS) {
            if (st.equalsIgnoreCase(value)) return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SpecialToken && super.equals(obj);
    }
}

