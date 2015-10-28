package book_catalogue;

public class Author implements Comparable<Author> {
    private String firstName;
    private String secondName;

    public Author(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFirstName() { return this.firstName; }

    public String getSecondName() { return this.secondName; }

    public String getFullName() { return this.firstName + " " + this.secondName; }

    public int compareTo(Author other) {
        return this.secondName.compareTo(other.getSecondName());
    }

    @Override
    public String toString() {
        return this.secondName + ", " + this.firstName.charAt(0) + ".";
    }

}

