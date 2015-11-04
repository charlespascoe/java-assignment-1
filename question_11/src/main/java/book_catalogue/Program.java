package book_catalogue;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import book_catalogue.query.QueryBuilder;
import book_catalogue.query.Condition;
import book_catalogue.query.QueryParsingException;

public class Program {
    public static void main(String[] args) {
        List<Book> books = new ArrayList<Book>();

        books.add(new Book("The Hitchhiker's Guide to the Galaxy", new Author("Douglas", "Adams"), "Megadodo Publications", 1979, BookStatus.AVAILABLE));
        books.add(new Book("Applied Cryptography", new Author("Bruce", "Schneier"), "John Wiley & Sons", 1994, BookStatus.ON_LOAN));
        books.add(new Book(
            "Introduction to Algorithms (3rd Edition)",
            new Author[] {
                new Author("Thomas", "Cormen"),
                new Author("Charles", "Leiserson"),
                new Author("Ronald", "Rivest"),
                new Author("Clifford", "Stein")
            },
            "MIT Press",
            2009,
            BookStatus.UNAVAILABLE
        ));

        Collections.sort(books);

        // for (Book b : books) {
        //    System.out.println(b);
        // }

        String query = "title ~~ 'to the' and not (author.first_name == 'douglas' and author.second_name == 'adams')";
        query = "title ~~ 'to the' and publication_year >= 2009";

        Condition c = null;

        try {
            c = QueryBuilder.buildQuery(query);
        } catch (QueryParsingException e) {
            StringBuilder str = new StringBuilder();
            str.append(query).append("\n");

            for (int i = 0; i < query.length(); i++) {
                if (i >= e.getStartPosition() && i <= e.getEndPosition()) {
                    str.append("^");
                } else {
                    str.append(" ");
                }
            }

            str.append("\n").append(e.getMessage());

            System.out.println(str);

        }

        if (c != null) {
            for (Book book : books) {
                if (c.isMatch(book)) {
                    System.out.println(book);
                }
            }
        }

    }
}

