package models;

//Declare Class and Encapsulated Variables
public class BillItem {

    private int itemId;
    private int billId;
    private int medicineId;
    private String medicineName;
    private double soldPrice;
    private int quantity;
    private double lineTotal;
    
    // Object creation & data initialization
    public BillItem() {
    }

    //Adding a new item to a bill 
    public BillItem(int medicineId, String medicineName, double soldPrice, int quantity) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.soldPrice = soldPrice;
        this.quantity = quantity;
        this.lineTotal = soldPrice * quantity;
    }

    //Fetching existing bill items
    public BillItem(int itemId, int billId, int medicineId, String medicineName, double soldPrice, int quantity, double lineTotal) {
        this.itemId = itemId;
        this.billId = billId;
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.soldPrice = soldPrice;
        this.quantity = quantity;
        this.lineTotal = lineTotal;
    }

    //Reading and Updating private fields
    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getBillId() {
        return billId;
    }
    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getMedicineId() {
        return medicineId;
    }
    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
        this.lineTotal = this.soldPrice * this.quantity;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.lineTotal = this.soldPrice * this.quantity;
    }

    public double getLineTotal() {
        return lineTotal;
    }
    public void setLineTotal(double lineTotal) {
        this.lineTotal = lineTotal;
    }
}
