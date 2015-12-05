package uk.co.cpascoe.caesar_shift_cracker;

//import java.lang.IllegalArgumentException;

public class InvalidKeyException extends IllegalArgumentException {
    public InvalidKeyException(String argName, Object value, String expectedValue) {
        super("Invalid Key Exception: " + argName + " = " + value + ", " + expectedValue);
    }
}

