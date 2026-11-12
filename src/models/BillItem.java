package models;

public class BillItem {

    private int item_id;
    private int bill_id;
    private int medicineId;
    private String medicineName;
    private double unitPrice;
    private int quantity;
    private double lineTotal;

    public BillItem() {
    }

    //Data Assigning (Creating Obects)
    public BillItem(int medicineId, String medicineName, double unitPrice, int quantity) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.lineTotal = unitPrice * quantity;
    }

    public BillItem(int item_id, int bill_id, int medicineId, String medicineName, double unitPrice, int quantity, double lineTotal) {
        this.item_id = item_id;
        this.bill_id = bill_id;
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.lineTotal = lineTotal;
    }

    //Accessing and Writting private fields
    public int getItem_id() {
        return item_id;
    }
    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getBill_id() {
        return bill_id;
    }
    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
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
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        this.lineTotal = this.unitPrice * this.quantity;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.lineTotal = this.unitPrice * this.quantity;
    }

    public double getLineTotal() {
        return lineTotal;
    }
    public void setLineTotal(double lineTotal) {
        this.lineTotal = lineTotal;
    }
}
