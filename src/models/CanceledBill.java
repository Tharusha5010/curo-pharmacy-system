package models;

import java.time.LocalDateTime;

public class CanceledBill {

    private int cancel_id;
    private int bill_id;
    private String canceled_by;
    private LocalDateTime canceled_at;
    private String reason;

    public CanceledBill() {
        this.canceled_at = LocalDateTime.now();
    }

    //Data Assigning (Creating Obects)
    public CanceledBill(int bill_id, String canceled_by, String reason) {
        this.bill_id = bill_id;
        this.canceled_by = (canceled_by != null) ? canceled_by.trim() : "ADMIN01";
        this.reason = (reason != null) ? reason.trim() : "No reason provided";
        this.canceled_at = LocalDateTime.now();
    }

    public CanceledBill(int cancel_id, int bill_id, String canceled_by, LocalDateTime canceled_at, String reason) {
        this.cancel_id = cancel_id;
        this.bill_id = bill_id;
        this.canceled_by = canceled_by;
        this.canceled_at = (canceled_at != null) ? canceled_at : LocalDateTime.now();
        this.reason = reason;
    }

    //Accessing and Writting private fields
    public int getCancel_id() {
        return cancel_id;
    }
    public void setCancel_id(int cancel_id) {
        this.cancel_id = cancel_id;
    }

    public int getBill_id() {
        return bill_id;
    }
    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public String getCanceled_by() {
        return canceled_by;
    }
    public void setCanceled_by(String canceled_by) {
        this.canceled_by = (canceled_by != null) ? canceled_by.trim() : "ADMIN01";
    }

    public LocalDateTime getCanceled_at() {
        return canceled_at;
    }
    public void setCanceled_at(LocalDateTime canceled_at) {
        this.canceled_at = (canceled_at != null) ? canceled_at : LocalDateTime.now();
    }

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = (reason != null) ? reason.trim() : "";
    }
}
