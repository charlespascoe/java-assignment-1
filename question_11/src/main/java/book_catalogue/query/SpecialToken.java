package book_catalogue.query;

public class SpecialToken extends Token {
    public static final String[] SPECIAL_TOKENS = new String[] { "(", ")", "==", "~~", ">", "<", ">=", "<=", "and", "or" };

    private String value;

    public SpecialToken(String value) {
        if (!SpecialToken.isSpecialToken(value)) {
            // ### Not a special token
        }

        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static boolean isSpecialToken(String value) {
        for (String st : SpecialToken.SPECIAL_TOKENS) {
            if (st.equals(value)) return true;
        }

        return false;
    }
}

