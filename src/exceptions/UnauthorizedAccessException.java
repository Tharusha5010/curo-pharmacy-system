package exceptions;

public class UnauthorizedAccessException extends Exception {
    
    //Default general error message
    public UnauthorizedAccessException() {
        super("Unauthorized Access. You do not have permission to perform this action.");
    }

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
