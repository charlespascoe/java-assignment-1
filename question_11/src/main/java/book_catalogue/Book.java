package book_catalogue;

import java.util.Arrays;

public class Book implements Comparable<Book> {
    private String title;
    private Author[] authors;
    private String publisher;
    private int publicationYear;
    private BookStatus status;

    public Book(String title, Author author, String publisher, int publicationYear, BookStatus status) {
        this(title, new Author[] { author }, publisher, publicationYear, status);
    }

    public Book(String title, Author[] authors, String publisher, int publicationYear, BookStatus status) {
        this.title = title;
        this.authors = Arrays.copyOf(authors, authors.length);
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.status = status;
    }

    public String getTitle() { return this.title; }

    public Author[] getAuthors() { return Arrays.copyOf(this.authors, this.authors.length); }

    public String getPublisher() { return this.publisher; }

    public int getPublicationYear() { return this.publicationYear; }

    public void setStatus(BookStatus status) {
        if (status == null) return;
        this.status = status;
    }

    public BookStatus getStatus() { return this.status; }

    public int compareTo(Book other) {
        int yearDiff = this.publicationYear - other.getPublicationYear();

        if (yearDiff != 0) return yearDiff;

        return Utils.compareArrays(this.authors, other.getAuthors());
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

