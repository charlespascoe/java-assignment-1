package book_catalogue.query;

import java.util.regex.Pattern;

public class NumericToken extends Token {
    private String value;

    public NumericToken(String token) {
        if (!NumericToken.isNumber(token)) {
            // ### Not a number!
        }

        this.value = token;
    }

    public String getValue() {
        return this.value;
    }

    public int getIntValue() {
        return Integer.parseInt(this.value);
    }

    public double getDoubleValue() {
        return Double.parseDouble(this.value);
    }

    public static boolean isNumber(String text) {
        return Pattern.compile("[0-9]+(\\.[0-9]+)?").matcher(text).matches();
    }

}

