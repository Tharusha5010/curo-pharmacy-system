package models;

import java.sql.Date;

//Declare Class and Encapsulated Variables
public class Medicine {

    private int medicineId;
    private String medicineName;
    private String companyName;
    private String batchNumber;
    private double unitPrice;
    private int stockQuantity;
    private Date stockDate;
    private Date expDate;
    private boolean requiresPrescription;
    private String movingType;

    // Object creation & data initialization
    public Medicine() {
    }
    
    public Medicine(int medicineId, String medicineName, String companyName, String batchNumber, double unitPrice, int stockQuantity, Date stockDate, Date expDate, boolean requiresPrescription, String movingType) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.companyName = companyName;
        this.batchNumber = batchNumber;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
        this.stockDate = stockDate;
        this.expDate = expDate;
        this.requiresPrescription = requiresPrescription;
        setMovingType(movingType);
    }


    //Fetching existing bill items
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


    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public String getBatchNumber() {
        return batchNumber;
    }
    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }


    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }


    public int getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }


    public Date getStockDate() {
        return stockDate;
    }
    public void setStockDate(Date stockDate) {
        this.stockDate = stockDate;
    }


    public Date getExpDate() {
        return expDate;
    }
    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }


    public boolean isRequiresPrescription() {
        return requiresPrescription;
    }
    public void setRequiresPrescription(boolean requiresPrescription) {
        this.requiresPrescription = requiresPrescription;
    }


    public String getMovingType() {
        return movingType;
    }
    public void setMovingType(String movingType) {
        this.movingType = (movingType != null && !movingType.trim().isEmpty()) ? movingType.trim().toUpperCase() : "SLOW";
    }
}
