package book_catalogue.query;

import java.util.List;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class QueryBuilder {

    public static List<Token> tokenise(String query) {
        // http://stackoverflow.com/questions/366202/regex-for-splitting-a-string-using-space-when-not-surrounded-by-single-or-double
        // (Accessed 29/10/2015)
        Pattern regex = Pattern.compile("\\(|\\)|[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
        Matcher matcher = regex.matcher(query);

        List<Token> tokens = new LinkedList<Token>();

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                // Double-quoted string
                tokens.add(new TextToken(matcher.group(1)));
            } else if (matcher.group(2) != null) {
                // Single-quoted string
                tokens.add(new TextToken(matcher.group(2)));
            } else {
                tokens.add(new TextToken(matcher.group()));
            }
        }

        return tokens;
    }
}

