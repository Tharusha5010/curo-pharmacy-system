package models;

import java.sql.Timestamp; // Updated to Timestamp for date + time tracking

public class CanceledBill {

//Declare Class and Encapsulated Variables
    private int cancelId;
	private int billId;
	private String canceledBy;
	private Timestamp canceledAt; // Updated to Timestamp for date + time tracking
	private String reason;

    public CanceledBill() {
    }

//Data Assigning (Creating Obects)
    public CanceledBill(int cancelId, int billId, String canceledBy, Timestamp canceledAt, String reason) {
        this.cancelId = cancelId;
        this.billId = billId;
        this.canceledBy = canceledBy;
        this.canceledAt = canceledAt;
        this.reason = reason;
    }

//Accessing and Writting private fields

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
        this.canceledBy = canceledBy;
    }

    public Timestamp getCanceledAt() {
        return canceledAt;
    }
    public void setCanceledAt(Timestamp canceledAt) {
        this.canceledAt = canceledAt;
    }

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
}