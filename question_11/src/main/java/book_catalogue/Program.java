package book_catalogue;

import book_catalogue.query.QueryBuilder;
import book_catalogue.query.Query;
import book_catalogue.query.QueryParsingException;
import java.io.Console;
import java.util.Collections;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        BookCatalogue books = new BookCatalogue();
        if (!books.load()) return;

        // Some additional books added programmatically, for examples
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

        Program.userQuery(books);
    }

    private static void userQuery(BookCatalogue books) {
        Console console = System.console();

        if (console == null) return;

        String input = "";

        while (true) {
            console.printf("%nBookCatalogue> ");

            input = console.readLine();

            if (input == null) return;

            // Print newline after user input
            console.printf("%n");

            if (input.toLowerCase().equals("quit")) {
                break;
            } else if (input.toLowerCase().equals("help")) {
                StringBuilder str = new StringBuilder();
                str.append("The commands are:\n")
                   .append("show <Book ID>\n")
                   .append("    Shows information about a book\n")
                   .append("checkout <Book ID>\n")
                   .append("    Check out a book\n")
                   .append("checkin <Book ID>\n")
                   .append("    Check in a book\n")
                   .append("remove <Book ID>\n")
                   .append("    Remove a book from the catalogue\n")
                   .append("query <query>\n")
                   .append("    Display a list of books, selected according to the provided query.\n\n");

                str.append("For query syntax, please see Assignment Document.\n");
                console.printf(str.toString());
            } else if (!input.equals("")) {
                String[] arr = input.split("\\s+", 2);
                String action = arr[0];
                String arguments = arr.length == 1 ? "" : arr[1];

                switch (action.toLowerCase()) {
                    case "checkout":
                    case "checkin":
                    case "remove":
                    case "show":
                        Book b = books.getBookByID(arguments);

                        if (b == null) {
                            console.printf("Book %s not found!%n", arguments);
                            break;
                        }

                        switch (action.toLowerCase()) {
                            case "checkout":
                                if (b.getStatus() == BookStatus.AVAILABLE) {
                                    b.setStatus(BookStatus.ON_LOAN);
                                    console.printf("Checked out [%s] %s%n", arguments, b.toString());
                                } else {
                                    console.printf("Cannot checkout '%s' as it is %s%n", b.getTitle(), b.getStatus().name());
                                }
                                break;
                            case "checkin":
                                if (b.getStatus() == BookStatus.ON_LOAN) {
                                    b.setStatus(BookStatus.AVAILABLE);
                                    console.printf("Checked in [%s] %s%n", arguments, b.toString());
                                } else {
                                    console.printf("Cannot checkin '%s' as it is %s%n", b.getTitle(), b.getStatus().name());
                                }
                                break;
                            case "remove":
                                console.printf("Removed [%s] %s%n", arguments, b.toString());
                                b.setStatus(BookStatus.UNAVAILABLE);
                                break;
                            case "show":
                                console.printf(b.displayInfo());
                                break;
                        }

                        break;
                    case "query":
                        Query query = null;

                        try {
                            query = QueryBuilder.buildQuery(arguments);
                        } catch (QueryParsingException ex) {
                            console.printf("%s%n", ex.getUserMessage(arguments));
                        }

                        if (query != null) {
                            List<Book> results = books.filter(query.getCondition());

                            if (query.getSorter() != null) {
                                Collections.sort(results, query.getSorter());
                            }

                            for (Book book : results) {
                                console.printf("[%s] %s (%s)%n", book.getID(), book, book.getStatus().name());
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

