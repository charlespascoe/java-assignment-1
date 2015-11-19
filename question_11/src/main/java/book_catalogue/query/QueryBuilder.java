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
        Condition conditon = QueryBuilder.buildAbstractSyntaxTree(tokens);

        return new Query(input, conditon, sorter);
    }

    private static Sorter buildSorter(BetterList<QueryComponent> tokens) throws QueryParsingException {
        int index = tokens.indexOf(new SpecialToken("sort"));

        if (index == -1) return null;

        List<BookFieldComparator> comparators = new LinkedList<BookFieldComparator>();

        BetterList<QueryComponent> sortTokens = tokens.take(index, tokens.size() - index);

        // Remove the "sort" SpecialToken
        SpecialToken sortToken = (SpecialToken)sortTokens.pop(0);

        if (sortTokens.size() == 0) {
            throw new QueryParsingException(sortToken.getEndPosition(), "Expected field after 'sort'");
        }

        while (sortTokens.size() > 0) {
            BetterList<QueryComponent> fieldTokens = sortTokens.takeUntilFound(new SpecialToken(","));
            System.out.println(fieldTokens.size());

            SpecialToken comma = null;

            if (fieldTokens.last().equals(new SpecialToken(","))) {
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

                if (SpecialToken.isSpecialToken(text)) {
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

    public static Condition buildAbstractSyntaxTree(List<QueryComponent> components) throws QueryParsingException {
        QueryBuilder.processBrackets(components);

        for (int i = 0; i < components.size(); i++) {
            QueryComponent c = components.get(i);
            if (c instanceof SpecialToken) {
                SpecialToken st = (SpecialToken)c;

                if (QueryBuilder.isCondition(st.getValue())) {
                    if (i + 1 == components.size()) {
                        throw new QueryParsingException(st.getEndPosition(), "Unexpected end of input");
                    }

                    if (i - 1 < 0) {
                        throw new QueryParsingException(0, "Expected text token before condition");
                    }

                    QueryComponent lefthandComponent = components.get(i - 1);
                    QueryComponent righthandComponent = components.get(i + 1);
                    QueryComponent result = null;

                    switch (st.getValue()) {
                        case "==":
                            if (righthandComponent instanceof TextToken) {
                                result = new TextEqualCondition(lefthandComponent, righthandComponent);
                            } else if (righthandComponent instanceof NumericToken) {
                                result = new NumericEqualCondition(lefthandComponent, righthandComponent);
                            } else {
                                throw new UnexpectedQueryComponentException(righthandComponent, "text or number");
                            }
                            break;
                        case "~~":
                            result = new TextContainsCondition(lefthandComponent, righthandComponent);
                            break;
                        case ">":
                            result = new NumericGreaterThanCondition(lefthandComponent, righthandComponent);
                            break;
                        case "<":
                            result = new NumericLessThanCondition(lefthandComponent, righthandComponent);
                            break;
                        case ">=":
                            result = new NumericGreaterThanOrEqualCondition(lefthandComponent, righthandComponent);
                            break;
                        case "<=":
                            result = new NumericLessThanOrEqualCondition(lefthandComponent, righthandComponent);
                            break;
                    }

                    Utils.spliceIntoList(components, i - 1, i + 1, result);
                    // Subtract 1 so that i points at
                    // the newly inserted element
                    i--;
                }
            }
        }

        for (int i = 0; i < components.size(); i++) {
            QueryComponent c = components.get(i);

            if (c instanceof SpecialToken) {
                SpecialToken st = (SpecialToken)c;

                if (st.getValue().equals("not")) {
                    if (i + 1 == components.size()) {
                        throw new QueryParsingException(st.getEndPosition(), "Unexpected end of input");
                    }

                    QueryComponent righthandComponent = components.get(i + 1);

                    Utils.spliceIntoList(components, i, i + 1, new NotCondition(righthandComponent));
                }
            }
        }

        for (int i = 0; i < components.size(); i++) {
            QueryComponent c = components.get(i);

            if (c instanceof SpecialToken) {
                SpecialToken st = (SpecialToken)c;

                if (st.getValue().equals("and")) {
                    if (i + 1 == components.size()) {
                        throw new QueryParsingException(st.getEndPosition(), "Unexpected end of input");
                    }

                    if (i - 1 < 0) {
                        throw new QueryParsingException(0, "Expected condition before 'and'");
                    }
                    QueryComponent lefthandComponent = components.get(i - 1);
                    QueryComponent righthandComponent = components.get(i + 1);

                    Utils.spliceIntoList(components, i - 1, i + 1, new LogicalAndCondition(lefthandComponent, righthandComponent));
                    // Subtract 1 so that i points at
                    // the newly inserted element
                    i--;
                }
            }
        }

        for (int i = 0; i < components.size(); i++) {
            QueryComponent c = components.get(i);

            if (c instanceof SpecialToken) {
                SpecialToken st = (SpecialToken)c;

                if (st.getValue().equals("or")) {
                    if (i + 1 == components.size()) {
                        throw new QueryParsingException(st.getEndPosition(), "Unexpected end of input");
                    }

                    if (i - 1 < 0) {
                        throw new QueryParsingException(0, "Expected condition before 'or'");
                    }
                    QueryComponent lefthandComponent = components.get(i - 1);
                    QueryComponent righthandComponent = components.get(i + 1);

                    Utils.spliceIntoList(components, i - 1, i + 1, new LogicalOrCondition(lefthandComponent, righthandComponent));
                    // Subtract 1 so that i points at
                    // the newly inserted element
                    i--;
                }
            }
        }

        if (components.size() > 1) {
            int index = components.get(1).getStartPosition();
            throw new QueryParsingException(index, "Unexpected query component");
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

    private static void processBrackets(List<QueryComponent> components) throws QueryParsingException {
        boolean bracketsFound;

        do {
            bracketsFound = false;

            for (int i = 0; i < components.size(); i++) {
                QueryComponent c = components.get(i);

                if (c instanceof SpecialToken) {
                    SpecialToken st = (SpecialToken)c;

                    if (st.getValue().equals("(")) {
                        int closeBracketIndex = QueryBuilder.findClosingBracket(i, components);

                        if (closeBracketIndex == -1) {
                            // ### Closing bracket not found!
                            throw new QueryParsingException(st.getStartPosition(), st.getEndPosition(), "Closing bracket not found!");
                        }

                        Condition result = QueryBuilder.buildAbstractSyntaxTree(Utils.subList(components, i + 1, closeBracketIndex - 1));

                        Utils.spliceIntoList(components, i, closeBracketIndex, result);

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

                if (st.getValue().equals("(")) {
                    bracketCount++;
                } else if (st.getValue().equals(")")) {
                    if (bracketCount == 0) return i;
                    bracketCount--;
                }
            }
        }

        return -1;
    }
}

