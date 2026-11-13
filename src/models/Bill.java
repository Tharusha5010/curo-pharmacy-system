package models;

import java.time.LocalDateTime;

public class Bill {

    //Declare Class and Encapsulated Variables
    private int billId;
    private String cashierId;
    private double totalAmount;
    private double discountAmount;
    private double netTotal;
    private boolean isPrescriptionChecked;
    private boolean isFreeIssue;
    private LocalDateTime createAt;

    //Data Assigning (Creating Obects)
    public Bill() {
        this.createAt = LocalDateTime.now();
    }

    public Bill(String cashierId, double totalAmount, double discountAmount, double netTotal, boolean isPrescriptionChecked, boolean isFreeIssue) {
        this.cashierId = cashierId;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.netTotal = netTotal;
        this.isPrescriptionChecked = isPrescriptionChecked;
        this.isFreeIssue = isFreeIssue;
        this.createAt = LocalDateTime.now();
    }

    public Bill(int billId, String cashierId, double totalAmount, double discountAmount, double netTotal, boolean isPrescriptionChecked, boolean isFreeIssue, LocalDateTime createAt) {
        this.billId = billId;
        this.cashierId = cashierId;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.netTotal = netTotal;
        this.isPrescriptionChecked = isPrescriptionChecked;
        this.isFreeIssue = isFreeIssue;
        this.createAt = (createAt != null) ? createAt : LocalDateTime.now();
    }

    //Accessing and Writting private fields
    public int getBillId() {
        return billId;
    }
    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getCashierId() {
        return cashierId;
    }
    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getNetTotal() {
        return netTotal;
    }
    public void setNetTotal(double netTotal) {
        this.netTotal = netTotal;
    }

    public boolean isIsPrescriptionChecked() {
        return isPrescriptionChecked;
    }
    public void setIsPrescriptionChecked(boolean isPrescriptionChecked) {
        this.isPrescriptionChecked = isPrescriptionChecked;
    }

    public boolean isIsFreeIssue() {
        return isFreeIssue;
    }
    public void setIsFreeIssue(boolean isFreeIssue) {
        this.isFreeIssue = isFreeIssue;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = (createAt != null) ? createAt : LocalDateTime.now();
    }
}