package exceptions;

public class InvalidStaffIdException extends Exception {
    
    //Default general error message
    public InvalidStaffIdException() {
        super("Invalid or Inactive Staff ID. Please Check Your Credentials and try again.");
    }

    public InvalidStaffIdException(String message) {
        super(message);
    }
}
