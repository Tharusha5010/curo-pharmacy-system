package exceptions;

public class OutOfStockException extends Exception {
    
    //Default general error message
    public OutOfStockException() {
        super("Requested Quantity exceeds available stock.");
    }

    public OutOfStockException(String message) {
        super(message);
    }
}
