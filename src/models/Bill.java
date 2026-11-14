package models;



import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;



//Declare Class and Encapsulated Variables
public class Bill {

    private int billId;
    private String cashierId;
    private double totalAmount;
    private double discountAmount;
    private String discounReason;
    private double netTotal;
    private boolean isPrescriptionChecked;
    private boolean isFreeIssue;
    private Timestamp createAt;
    private List<BillItem> items;

    

     // Object creation & data initialization
    public Bill() {
        this.items = new ArrayList<>();
    }


    //New Bills (before inserting into DB)
    public Bill(String cashierId, double totalAmount, double discountAmount, String discountReason, double netTotal, boolean isPrescriptionChecked, boolean isFreeIssue, Timestamp createdAt) {
        this.cashierId = cashierId;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.discountReason = discountReason;
        this.netTotal = netTotal;
        this.isPrescriptionChecked = isPrescriptionChecked;
        this.isFreeIssue = isFreeIssue;
        this.createAt = createdAt;
        this.item =new ArrayList<>();
    }

    
    //Existing Bills (when loading from DB)
     public Bill(int billId, String cashierId, double totalAmount, double discountAmount, String discountReason, double netTotal, boolean isPrescriptionChecked, boolean isFreeIssue, Timestamp createdAt) {
        this.billId = billId;
        this.cashierId = cashierId;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.discountReason = discountReason;
        this.netTotal = netTotal;
        this.isPrescriptionChecked = isPrescriptionChecked;
        this.isFreeIssue = isFreeIssue;
        this.createAt = createdAt;
        this.item =new ArrayList<>();
    }


    
    //Reading and Updating private fields
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

    
     public String getDiscountReason() {
        return discountReason;
    }
    public void setDiscountReason(String discountReason) {
        this.discountReason = discountReason;
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
        isPrescriptionChecked = prescriptionChecked;
    }

    
    public boolean isFreeIssue() {
        return isFreeIssue;
    }
    public void setFreeIssue(boolean freeIssue) {
        this.isFreeIssue = freeIssue;
    }

    
    public Timestamp getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }


    public List<BillItem>getItems() {
        return items;
    }
    public void setItems(List<BillItem> items) {
        this.items = (items != null) ? items : new ArrayList<>();
    }
}
