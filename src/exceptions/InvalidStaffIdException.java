package exceptions;

public class InvalidStaffIdException extends Exception {
    public InvalidStaffIdException() {
        super("Invalid or inactive Staff ID. Please check your credentials and try again.");
    }

    public InvalidStaffIdException(String message) {
        super(message);
    }
}