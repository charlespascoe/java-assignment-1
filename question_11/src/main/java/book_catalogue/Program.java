package book_catalogue;

import book_catalogue.query.QueryBuilder;
import book_catalogue.query.Query;
import book_catalogue.query.QueryParsingException;
import java.io.Console;
import java.util.Collections;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        Table<Book> t = new Table<Book>(new TableFormatter<Book>() {
            @Override
            public String[] getFields() { return new String[0]; }

            @Override
            public String getField(Book item, String field) { return ""; }
        });

        System.out.println(t.toString());

        BookCatalogue books = new BookCatalogue();

        books.add(new Book("#000042", "The Hitchhiker's Guide to the Galaxy", new Author("Douglas", "Adams"), "Megadodo Publications", 1979, BookStatus.AVAILABLE));
        books.add(new Book("#065537", "Applied Cryptography", new Author("Bruce", "Schneier"), "John Wiley & Sons", 1994, BookStatus.ON_LOAN));
        books.add(new Book(
            "#333333",
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

        books.sort();

        // String query = "title ~~ 'to' and publication_year < 2000 and not (author.first_name ~~ 'douglas' and author.second_name ~~ 'adams')";
        // query = "status == unavailable";

        Program.userQuery(books);

    }

    private static void userQuery(BookCatalogue books) {
        Console console = System.console();

        if (console == null) return;

        String input = "";

        while (true) {
            System.out.print("query> ");

            input = console.readLine();

            if (input == null) return;

            if (input.toLowerCase().equals("quit")) {
                break;
            } else if (input.toLowerCase().equals("help")) {
                System.out.println("No help yet!!!\n\n");
            } else if (!input.equals("")) {
                String[] arr = input.split("[\\ ]+", 2);
                String action = arr[0];
                String arguments = arr[1];

                switch (action.toLowerCase()) {
                    case "checkout":
                        break;
                    case "checkin":
                        break;
                    case "remove":
                        break;
                    case "query":
                        Query query = null;

                        try {
                            query = QueryBuilder.buildQuery(arguments);
                        } catch (QueryParsingException ex) {
                            System.out.println(ex.getUserMessage(arguments));
                        }

                        if (query != null) {
                            List<Book> results = books.filter(query.getCondition());

                            if (query.getSorter() != null) {
                                Collections.sort(results, query.getSorter());
                            }

                            for (Book book : results) {
                                System.out.println(book);
                            }
                        }
                        break;
                    default:
                        break;
                }

            }
        }
    }
}

