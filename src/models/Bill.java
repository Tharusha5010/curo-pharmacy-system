package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Bill {

    private int billId;
    private String cashierId;
    private double totalAmount;
    private double discountAmount;
    private double netTotal;
    private boolean isPrescriptionChecked;
    private boolean isFreeIssue;
    private LocalDateTime createdAt;
    private List<BillItem> items;

    public Bill() {
        this.items = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.totalAmount = 0.0;
        this.discountAmount = 0.0;
        this.netTotal = 0.0;
        this.isPrescriptionChecked = false;
        this.isFreeIssue = false;
    }

    //Data Assigning (Creating Obects)
    public Bill(String cashierId) {
        this.cashierId = (cashierId != null) ? cashierId.trim() : "ADMIN01";
        this.createdAt = LocalDateTime.now();
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
        this.discountAmount = 0.0;
        this.netTotal = 0.0;
        this.isPrescriptionChecked = false;
        this.isFreeIssue = false;
    }

    public Bill(int billId, String cashierId, double totalAmount, double discountAmount, double netTotal, boolean isPrescriptionChecked, boolean isFreeIssue, LocalDateTime createdAt) {
        this.billId = billId;
        this.cashierId = cashierId;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.netTotal = netTotal;
        this.isPrescriptionChecked = isPrescriptionChecked;
        this.isFreeIssue = isFreeIssue;
        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
        this.items = new ArrayList<>();
    }

    // Business Logic Methods
    
    public void addItem(BillItem item) {
        if (item != null) {
            this.items.add(item);
            recalculateTotals();
        }
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            this.items.remove(index);
            recalculateTotals();
        }
    }

    public void applyFivePercentDiscount() {
        if (!isFreeIssue) {
            this.discountAmount = this.totalAmount * 0.05;
        } else {
            this.discountAmount = 0.0;
        }
        recalculateTotals();
    }

    public void setFreeIssue(boolean freeIssue) {
        this.isFreeIssue = freeIssue;
        if (freeIssue) {
            for (BillItem item : items) {
                item.setUnitPrice(0.0);
            }
            this.discountAmount = 0.0;
        }
        recalculateTotals();
    }

    public void recalculateTotals() {
        double sum = 0.0;
        for (BillItem item : items) {
            sum += item.getLineTotal();
        }
        this.totalAmount = sum;
        if (isFreeIssue) {
            this.totalAmount = 0.0;
            this.discountAmount = 0.0;
            this.netTotal = 0.0;
        } else {
            this.netTotal = Math.max(0.0, this.totalAmount - this.discountAmount);
        }
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
        this.cashierId = (cashierId != null) ? cashierId.trim() : "ADMIN01";
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
        recalculateTotals();
    }

    public double getNetTotal() {
        return netTotal;
    }
    public void setNetTotal(double netTotal) {
        this.netTotal = netTotal;
    }

    public boolean isPrescriptionChecked() {
        return isPrescriptionChecked;
    }
    public void setPrescriptionChecked(boolean prescriptionChecked) {
        this.isPrescriptionChecked = prescriptionChecked;
    }

    public boolean isFreeIssue() {
        return isFreeIssue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
    }

    public List<BillItem> getItems() {
        return items;
    }
    public void setItems(List<BillItem> items) {
        this.items = (items != null) ? items : new ArrayList<>();
        recalculateTotals();
    }
}








