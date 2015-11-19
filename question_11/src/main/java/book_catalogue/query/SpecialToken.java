package book_catalogue.query;

public class SpecialToken extends Token {
    public static enum TokenConstant {
        OPEN_BRACKET ("("),
        CLOSE_BRACKET (")"),
        EQUALS ("=="),
        CONTAINS ("~~"),
        GREATER_THAN (">"),
        LESS_THAN ("<"),
        GREATER_THAN_OR_EQUAL (">="),
        LESS_THAN_OR_EQUAL("<="),
        AND ("and"),
        OR ("or"),
        NOT ("not"),
        SORT ("sort"),
        ASCENDING ("asc"),
        DESCENDING ("desc"),
        COMMA (",");

        private final String tokenConst;

        private TokenConstant(String tokenConst) {
            this.tokenConst = tokenConst;
        }

        public String toString() {
            return this.tokenConst;
        }

        public SpecialToken toToken() {
            return new SpecialToken(this);
        }

        public static boolean isSpecialToken(String text) {
            for (TokenConstant token : TokenConstant.values()) {
                if (token.toString().equalsIgnoreCase(text)) return true;
            }
            return false;
        }

        public static TokenConstant fromString(String text) {
            for (TokenConstant token : TokenConstant.values()) {
                if (token.toString().equalsIgnoreCase(text)) return token;
            }

            // Using unchecked exception, since this should never occur
            // as the code should always check to see if the text is
            // a valid field prior to calling this method
            throw new Error("Can't find SpecialToken " + text + " - make sure you check that it's a valid SpecialToken first!");
        }

    }

    private TokenConstant tokenConstant;

    public SpecialToken(TokenConstant token) {
        super(0, token.toString());
        this.tokenConstant = token;
    }

    public SpecialToken(String value) throws QueryParsingException {
        this(0, value);
    }

    public SpecialToken(int startPos, String value) throws QueryParsingException {
        super(startPos, value);

        if (!SpecialToken.TokenConstant.isSpecialToken(value)) {
            throw new QueryParsingException(
                this.getStartPosition(),
                this.getEndPosition(),
                "Not a valid special token: " + value
            );
        }

        this.tokenConstant = TokenConstant.fromString(value);
    }

    public TokenConstant getTokenConstant() {
        return this.tokenConstant;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SpecialToken && super.equals(obj);
    }
}

