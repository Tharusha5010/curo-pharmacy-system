package exceptions;

public class ExpiredMedicineException extends Exception {

    //Default general error message
    public ExpiredMedicineException() {
        super("Medicine is Expired. Please check the expiry date before issuing.");
    }

    public ExpiredMedicineException(String message) {
        super(message);
    }
}
