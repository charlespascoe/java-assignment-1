package book_catalogue.query;

import java.util.List;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import book_catalogue.Utils;
import book_catalogue.BetterList;

public class QueryBuilder {
    public static Query buildQuery(String input) throws QueryParsingException {
        BetterList<QueryComponent> tokens = QueryBuilder.tokenise(input);

        Sorter sorter = QueryBuilder.buildSorter(tokens);
        Condition conditon;

        if (tokens.size() > 0) {
            conditon = QueryBuilder.buildAbstractSyntaxTree(tokens);
        } else {
            conditon = new AlwaysTrueCondition();
        }

        return new Query(input, conditon, sorter);
    }

    private static Sorter buildSorter(BetterList<QueryComponent> tokens) throws QueryParsingException {
        int index = tokens.indexOf(SpecialToken.TokenConstant.SORT.toToken());

        if (index == -1) return null;

        List<BookFieldComparator> comparators = new LinkedList<BookFieldComparator>();

        BetterList<QueryComponent> sortTokens = tokens.take(index, tokens.size() - index);

        // Remove the "sort" SpecialToken
        SpecialToken sortToken = (SpecialToken)sortTokens.pop(0);

        if (sortTokens.size() == 0) {
            throw new QueryParsingException(sortToken.getEndPosition(), "Expected field after '" + SpecialToken.TokenConstant.SORT.toString() + "'");
        }

        while (sortTokens.size() > 0) {
            BetterList<QueryComponent> fieldTokens = sortTokens.takeUntilFound(new SpecialToken(","));

            SpecialToken comma = null;

            if (fieldTokens.last().equals(SpecialToken.TokenConstant.COMMA.toToken())) {
                comma = (SpecialToken)fieldTokens.pop();
            }

            switch (fieldTokens.size()) {
                case 0:
                    throw new QueryParsingException(comma.getStartPosition(), "Expected field before comma");
                case 1:
                    comparators.add(new BookFieldComparator(fieldTokens.get(0)));
                    break;
                case 2:
                    comparators.add(new BookFieldComparator(fieldTokens.get(0), fieldTokens.get(1)));
                    break;
                default:
                    // Too many tokens
                    int startIndex = fieldTokens.get(2).getStartPosition();
                    int endIndex = fieldTokens.last().getEndPosition();

                    throw new QueryParsingException(startIndex, endIndex, "Too many tokens");
            }
        }

        return new Sorter(comparators.toArray(new BookFieldComparator[0]));
    }

    public static BetterList<QueryComponent> tokenise(String query) throws QueryParsingException {
        // http://stackoverflow.com/questions/366202/regex-for-splitting-a-string-using-space-when-not-surrounded-by-single-or-double
        // (Accessed 29/10/2015)
        Pattern regex = Pattern.compile("\\(|\\)|,|[^\\s\"'\\),]+|\"([^\"]*)\"|'([^']*)'");
        Matcher matcher = regex.matcher(query);

        BetterList<QueryComponent> tokens = new BetterList<>(new LinkedList<QueryComponent>());

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                // Double-quoted string
                tokens.add(new TextToken(matcher.start(1), matcher.group(1)));
            } else if (matcher.group(2) != null) {
                // Single-quoted string
                tokens.add(new TextToken(matcher.start(2), matcher.group(2)));
            } else {
                int startIndex = matcher.start();
                String text = matcher.group();

                if (SpecialToken.TokenConstant.isSpecialToken(text)) {
                    tokens.add(new SpecialToken(startIndex, text));
                } else if (NumericToken.isNumber(text)) {
                    tokens.add(new NumericToken(startIndex, text));
                } else {
                    tokens.add(new TextToken(startIndex, text));
                }
            }
        }

        return tokens;
    }

    public static Condition buildAbstractSyntaxTree(BetterList<QueryComponent> components) throws QueryParsingException {
        QueryBuilder.processBrackets(components);

        for (SpecialToken.TokenConstant conditionTokenConstant : Utils.union(TextCondition.TEXT_CONDITIONS, NumericCondition.NUMERIC_CONDITIONS)) {
            while (true) {
                int index = components.indexOf(conditionTokenConstant.toToken());

                if (index == -1) break;

                SpecialToken conditionToken = (SpecialToken)components.get(index);

                if (index + 1 == components.size()) {
                    throw new QueryParsingException(conditionToken.getEndPosition(), "Unexpected end of input");
                }

                if (index - 1 < 0) {
                    throw new QueryParsingException(0, "Expected text token before condition");
                }

                QueryComponent lefthandComponent = components.get(index - 1);
                QueryComponent righthandComponent = components.get(index + 1);
                QueryComponent result = null;

                if (conditionToken.getTokenConstant() == SpecialToken.TokenConstant.EQUALS) {
                    if (righthandComponent instanceof TextToken) {
                        result = new TextCondition(lefthandComponent, conditionToken, righthandComponent);
                    } else if (righthandComponent instanceof NumericToken) {
                        result = new NumericCondition(lefthandComponent, conditionToken, righthandComponent);
                    } else {
                        throw new UnexpectedQueryComponentException(righthandComponent, "text or number");
                    }
                } else if (TextCondition.isTextConditionToken(conditionToken)) {
                    result = new TextCondition(lefthandComponent, conditionToken, righthandComponent);
                } else if (NumericCondition.isNumericConditionToken(conditionToken)) {
                    result = new NumericCondition(lefthandComponent, conditionToken, righthandComponent);
                } else {
                    // throw error!
                }

                components.splice(index - 1, index + 1, result);
            }
        }

        while (true) {
            int index = components.indexOf(new SpecialToken(SpecialToken.TokenConstant.NOT));

            if (index == -1) break;

            SpecialToken notToken = (SpecialToken)components.get(index);

            if (index + 1 == components.size()) {
                throw new QueryParsingException(notToken.getEndPosition(), "Unexpected end of input");
            }

            QueryComponent righthandComponent = components.get(index + 1);

            components.splice(index, index + 1, new NotCondition(notToken, righthandComponent));
        }

        for (SpecialToken.TokenConstant logicalConditionTokenConstant : LogicalCondition.LOGICAL_CONDITIONS) {
            while (true) {
                int index = components.indexOf(new SpecialToken(logicalConditionTokenConstant));

                if (index == -1) break;

                SpecialToken logicalConditionToken = (SpecialToken)components.get(index);

                if (index + 1 == components.size()) {
                    throw new QueryParsingException(logicalConditionToken.getEndPosition(), "Unexpected end of input");
                }

                if (index - 1 < 0) {
                    throw new QueryParsingException(0, "Expected condition before logical operator");
                }

                QueryComponent lefthandComponent = components.get(index - 1);
                QueryComponent righthandComponent = components.get(index + 1);

                components.splice(index - 1, index + 1, new LogicalCondition(lefthandComponent, logicalConditionToken, righthandComponent));
            }
        }

        if (components.size() > 1) {
            throw new QueryParsingException(components.get(1).getStartPosition(), components.last().getEndPosition(), "Unexpected query component(s)");
        }

        QueryComponent qc = components.get(0);

        if (!(qc instanceof Condition)) {
            throw new UnexpectedQueryComponentException(qc, "condition");
        }

        return (Condition)qc;
    }

    private static boolean isCondition(String text) {
        return
            text.equals("==") ||
            text.equals("~~") ||
            text.equals(">") ||
            text.equals("<") ||
            text.equals(">=") ||
            text.equals("<=");
    }

    private static void processBrackets(BetterList<QueryComponent> components) throws QueryParsingException {
        boolean bracketsFound;

        do {
            bracketsFound = false;

            for (int i = 0; i < components.size(); i++) {
                QueryComponent c = components.get(i);

                if (c instanceof SpecialToken) {
                    SpecialToken st = (SpecialToken)c;

                    if (st.getTokenConstant() == SpecialToken.TokenConstant.OPEN_BRACKET) {
                        int closeBracketIndex = QueryBuilder.findClosingBracket(i, components);

                        if (closeBracketIndex == -1) {
                            throw new QueryParsingException(st.getStartPosition(), st.getEndPosition(), "Closing bracket not found");
                        }

                        Condition result = QueryBuilder.buildAbstractSyntaxTree(new BetterList<QueryComponent>(Utils.subList(components, i + 1, closeBracketIndex - 1)));

                        components.splice(i, closeBracketIndex, result);

                        bracketsFound = true;
                    }
                }
            }
        } while (bracketsFound);
    }

    private static int findClosingBracket(int openBracketIndex, List<QueryComponent> components) {
        int bracketCount = 0;
        for (int i = openBracketIndex + 1; i < components.size(); i++) {
            QueryComponent c = components.get(i);

            if (c instanceof SpecialToken) {
                SpecialToken st = (SpecialToken)c;

                if (st.getTokenConstant() == SpecialToken.TokenConstant.OPEN_BRACKET) {
                    bracketCount++;
                } else if (st.getTokenConstant() == SpecialToken.TokenConstant.CLOSE_BRACKET) {
                    if (bracketCount == 0) return i;
                    bracketCount--;
                }
            }
        }

        return -1;
    }
}

