package book_catalogue;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import book_catalogue.query.QueryBuilder;
import book_catalogue.query.Condition;

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

        Condition c = QueryBuilder.buildQuery("title ~~ 'to the' and not (author.first_name == 'douglas' and author.second_name == 'adams')");

        for (Book book : books) {
            if (c.isMatch(book)) {
                System.out.println(book);
            }
        }

    }
}

