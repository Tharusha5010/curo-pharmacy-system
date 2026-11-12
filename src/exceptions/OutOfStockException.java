package exceptions;

public class OutOfStockException extends Exception {
    public OutOfStockException() {
        super("Requested quantity exceeds available stock.");
    }

    public OutOfStockException(String message) {
        super(message);
    }
}