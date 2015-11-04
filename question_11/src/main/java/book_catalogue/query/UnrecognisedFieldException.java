package book_catalogue.query;

public class UnrecognisedFieldException extends QueryParsingException {
    public UnrecognisedFieldException(TextToken field, String expectedFieldType) {
        super(
            field.getStartPosition(),
            field.getEndPosition(),
            "Unregcogised field name: " + field.getValue() + ". Exptecting " + expectedFieldType + " field type"
        );
    }
}

