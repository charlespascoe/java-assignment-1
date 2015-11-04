package book_catalogue;

import book_catalogue.query.QueryBuilder;
import book_catalogue.query.Condition;
import book_catalogue.query.QueryParsingException;
import java.io.Console;

public class Program {
    public static void main(String[] args) {
        BookCatalogue books = new BookCatalogue();

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

            if (input == null || input.toLowerCase().equals("quit")) {
                break;
            } else if (input.toLowerCase().equals("help")) {
                System.out.println("No help yet!!!\n\n");
            } else if (!input.equals("")) {
                Condition cond = null;

                try {
                    cond = QueryBuilder.buildQuery(input);
                } catch (QueryParsingException ex) {
                    StringBuilder str = new StringBuilder();
                    str.append(input).append("\n");

                    for (int i = 0; i < input.length(); i++) {
                        if (i >= ex.getStartPosition() && i <= ex.getEndPosition()) {
                            str.append("^");
                        } else {
                            str.append(" ");
                        }
                    }

                    str.append("\n").append(ex.getMessage());

                    System.out.println(str);
                }

                if (cond != null) {
                    for (Book book : books.filter(cond)) {
                        System.out.println(book);
                    }
                }
            }
        }
    }
}

