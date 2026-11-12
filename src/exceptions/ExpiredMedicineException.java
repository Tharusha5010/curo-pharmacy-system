package exceptions;

public class ExpiredMedicineException extends Exception {
    public ExpiredMedicineException() {
        super("Medicine is expired. Please check the expiry date before issuing.");
    }

    public ExpiredMedicineException(String message) {
        super(message);
    }
}