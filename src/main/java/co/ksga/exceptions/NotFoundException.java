package co.ksga.exceptions;

public class NotFoundException extends RuntimeException {

    // Constructor with a custom message
    public NotFoundException(String message) {
        super(message);
    }
    // Constructor with a custom message and a cause (for chaining exceptions)
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}