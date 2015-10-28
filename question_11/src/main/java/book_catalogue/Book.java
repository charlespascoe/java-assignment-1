package book_catalogue;

import java.util.Calendar;

public class Book implements Comparable<Book> {
    private String title;
    private String authorFirstName;
    private String authorSecondName;
    private Calendar publicationDate;

    public Book(String title, String authorFirstName, String authorSecondName, Calendar publicationDate) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorSecondName = authorSecondName;
        this.publicationDate = publicationDate;
    }

    public String getTitle() { return this.title; }

    public String getAuthorFirstName() { return this.authorFirstName; }

    public String getAuthorSecondName() { return this.authorSecondName; }

    public String getAuthorFullName() { return this.authorFirstName + " " + this.authorSecondName; }

    public Calendar getPublicationDate() { return (Calendar)this.publicationDate.clone(); }

    public int compareTo(Book other) {
        int yearDiff = this.publicationDate.get(Calendar.YEAR) - other.getPublicationDate().get(Calendar.YEAR);

        if (yearDiff != 0) return yearDiff;

        return this.authorSecondName.compareTo(other.getAuthorSecondName());
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(this.authorSecondName).append(", ").append(this.authorFirstName.charAt(0)).append(". ");
        str.append("(").append(this.publicationDate.get(Calendar.YEAR)).append(")");
        str.append(" \"").append(this.title).append("\"");

        return str.toString();
    }
}

