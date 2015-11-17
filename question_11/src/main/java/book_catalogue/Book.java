package book_catalogue;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Book implements Comparable<Book> {
    public static enum Field {
        ID ("id"),
        TITLE ("title"),
        AUTHOR_FIRST_NAME ("author.first_name"),
        AUTHOR_SECOND_NAME ("author.second_name"),
        PUBLISHER ("publisher"),
        PUBLICATION_YEAR ("publication_year"),
        STATUS ("status");

        private final String name;

        private Field(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public static Field fromString(String text) {
            for (Field field : Field.values()) {
                if (field.toString().equals(text.toLowerCase())) {
                    return field;
                }
            }

            // Using unchecked exception, since this should never occur
            // as the code should always check to see if the text is
            // a valid field prior to calling this method
            throw new Error("Can't find Field " + text + " - make sure you check that it's a valid field first!");
        }
    }

    private String id;
    private String title;
    private Author[] authors;
    private String publisher;
    private int publicationYear;
    private BookStatus status;

    public Book(String id, String title, Author author, String publisher, int publicationYear, BookStatus status) {
        this(id, title, new Author[] { author }, publisher, publicationYear, status);
    }

    public Book(String id, String title, Author[] authors, String publisher, int publicationYear, BookStatus status) {
        this.id = id;
        this.title = title;
        this.authors = Arrays.copyOf(authors, authors.length);
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.status = status;
        Arrays.sort(this.authors);
    }

    public String getID() { return this.id; }

    public String getTitle() { return this.title; }

    public Author[] getAuthors() { return Arrays.copyOf(this.authors, this.authors.length); }

    public String getPublisher() { return this.publisher; }

    public int getPublicationYear() { return this.publicationYear; }

    public void setStatus(BookStatus status) {
        if (status == null) return;
        this.status = status;
    }

    public BookStatus getStatus() { return this.status; }

    public Object getField(Field field) {
        switch (field) {
            case ID:
                return new Integer(this.id);
            case TITLE:
                return this.title;
            case AUTHOR_FIRST_NAME:
            case AUTHOR_SECOND_NAME:
                String[] names = new String[this.authors.length];

                for (int i = 0; i < names.length; i++) {
                    if (field == Field.AUTHOR_FIRST_NAME) {
                        names[i] = this.authors[i].getFirstName();
                    } else {
                        names[i] = this.authors[i].getSecondName();
                    }
                }

                return names;
            case PUBLISHER:
                return this.publisher;
            case PUBLICATION_YEAR:
                return new Integer(this.publicationYear);
            case STATUS:
                return this.status.name();
            default:
                return null;
        }
    }

    public int compareTo(Book other) {
        int yearDiff = this.publicationYear - other.getPublicationYear();

        if (yearDiff != 0) return yearDiff;

        return Utils.compareArrays(this.authors, other.getAuthors());
    }

    public int compareTo(Book other, Field field) {
        if (field == Field.AUTHOR_FIRST_NAME || field == Field.AUTHOR_SECOND_NAME) {
            return Utils.compareArrays((String[])this.getField(field), (String[])other.getField(field));
        } else {
            Comparable c = (Comparable)this.getField(field);
            return c.compareTo(other.getField(field));
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(Utils.join(Arrays.copyOf(this.authors, this.authors.length - 1), ", "));

        if (str.length() > 0) {
            if (this.authors.length > 2) {
                str.append(",");
            }

            str.append(" and ");
        }

        str.append(this.authors[this.authors.length - 1]);

        str.append(" (").append(this.publicationYear).append(")");
        str.append(" \"").append(this.title).append("\", ").append(this.publisher);

        return str.toString();
    }
}

