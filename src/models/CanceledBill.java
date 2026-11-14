package models;



import java.sql.Timestamp; 



//Declare Class and Encapsulated Variables
public class CanceledBill {

    private int cancelId;
	private int billId;
	private String canceledBy;
	private Timestamp canceledAt; 
	private String reason;


	
    public CanceledBill() {
		this.canceledAt = new Timestamp(System.currentTimeMllis());
    }
	

	//Create a new Cancellation Record
    public CanceledBill(int billId, String canceledBy, String reason) {
        this.billId = billId;
        setCanceledBy(canceledBy);
		setReason(reason);
		this.canceledAt = new Timestamp(System.currentTimeMillis()):
    }
	

	//Load Existing Records
    public CanceledBill(int cancelId, int billId, String canceledBy, Timestamp canceledAt, String reason) {
        this.cancelId = cancelId;
        this.billId = billId;
        setCanceledBy(canceledBy);
		setCanceledAt(canceledAt);
		setReason(reason);
    }


	
	//Reading and Updating private fields
    public int getCancelId() {
        return cancelId;
    }
    public void setCancelId(int cancelId) {
        this.cancelId = cancelId;
    }


    public int getBillId() {
        return billId;
    }
    public void setBillId(int billId) {
        this.billId = billId;
    }


    public String getCanceledBy() {
        return canceledBy;
    }

    public void setCanceledBy(String canceledBy) {
        this.canceledBy = (canceledBy != null && !canceledBy.trim().isEmpty()) ? canceledBy.trim() : "Unknown";
    }

	
    public Timestamp getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(Timestamp canceledAt) {
        this.canceledAt = (canceledAt != null) ? canceledAt : new Timestamp(System.currentTimeMillis());
    }

	
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = (reason != null && !reason.trim().isEmpty()) ? reason.trim() : "No reason provided";
    }
    

	//Display Readable bill details
    @Override
    public String toString() {
        return "CanceledBill [cancelId=" + cancelId + ", billId=" + billId + ", canceledBy=" + canceledBy
                + ", canceledAt=" + canceledAt + ", reason=" + reason + "]";
    }
}
