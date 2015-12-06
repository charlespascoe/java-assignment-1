package book_catalogue;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.*;

public class BookCatalogue extends ArrayList<Book> {
    public void sort() {
        Collections.sort(this);
    }

    public List<Book> filter(Matcher<Book> cond) {
        List<Book> results = new ArrayList<Book>();

        for (Book book : this) {
            if (cond.isMatch(book)) {
                results.add(book);
            }
        }

        return results;
    }

    public Book getBookByID(String id) {
        id = id.toLowerCase();
        for (Book book : this) {
            if (book.getID().toLowerCase().equals(id)) {
                return book;
            }
        }

        return null;
    }

    public boolean load() {
        try (JsonReader reader = new JsonReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("books.json")))) {
            reader.beginArray();

            Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

            while (reader.hasNext()) {
                this.add((Book)gson.fromJson(reader, Book.class));
            }

            return true;
        } catch (IOException ex) {
            System.err.println("Failed to open data file: " + ex.toString());
        }

        return false;
    }
}

