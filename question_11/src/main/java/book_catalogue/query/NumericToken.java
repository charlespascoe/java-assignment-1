package book_catalogue.query;

import java.util.regex.Pattern;

public class NumericToken extends Token {
    public NumericToken(int startPos, String value) throws QueryParsingException {
        super(startPos, value);

        if (!NumericToken.isNumber(value)) {
            throw new QueryParsingException(
                this.getStartPosition(),
                this.getEndPosition(),
                "Not a valid number: " + value
            );
        }
    }

    public int getIntValue() {
        return Integer.parseInt(this.getValue());
    }

    public double getDoubleValue() {
        return Double.parseDouble(this.getValue());
    }

    public static boolean isNumber(String text) {
        return Pattern.compile("[0-9]+(\\.[0-9]+)?").matcher(text).matches();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NumericToken && super.equals(obj);
    }

    @Override
    public String toString() {
        return "numeric token";
    }
}

