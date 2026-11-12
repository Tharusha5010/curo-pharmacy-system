package exceptions;

public class UnauthorizedAccessException extends Exception {
    public UnauthorizedAccessException() {
        super("Unauthorized access. This action requires OWNER privileges.");
    }

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}