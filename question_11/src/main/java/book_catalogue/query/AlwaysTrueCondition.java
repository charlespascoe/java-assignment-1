package book_catalogue.query;

import book_catalogue.Book;

public class AlwaysTrueCondition extends Condition {
    public AlwaysTrueCondition() {
        super(0,0);
    }

    @Override
    public boolean isMatch(Book book) {
        return true;
    }
}

