package book_catalogue;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        List<Book> books = new ArrayList<Book>();

        books.add(new Book("The Hitchhiker's Guide to the Galaxy", new Author("Douglas", "Adams"), "Megadodo Publications", 1979));
        books.add(new Book("Applied Cryptography", new Author("Bruce", "Schneier"), "John Wiley & Sons", 1994));
        books.add(new Book(
            "Introduction to Algorithms (3rd Edition)",
            new Author[] {
                new Author("Thomas", "Cormen"),
                new Author("Charles", "Leiserson"),
                new Author("Ronald", "Rivest"),
                new Author("Clifford", "Stein")
            },
            "MIT Press",
            2009
        ));

        Collections.sort(books);

        for (Book b : books) {
            System.out.println(b);
        }
    }
}

