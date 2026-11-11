package models;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    // Default Constructor(Helps InventoryDAO or GUI to create an empty Medicine object).
    public Medicine() {
    }

    // Parameterized Constructor
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
        this.movingType = (movingType != null) ? movingType.toUpperCase() : "SLOW"; // Default to "SLOW" if null
    }


    //Should be Notify.(ExpiredMedicineException.java = Sachintha Dilsara part)
    // Method to check if the medicine is expired
    public boolean isExpired() {
        if (expDate == null) {
            return false; // If no expiration date is set, consider it not expired
        }
        LocalDate today = LocalDate.now();
        LocalDate expiration = expDate.toLocalDate();
        return today.isAfter(expiration);
    }


    //Should be Notify.(Notification.java = Sachintha Dilsara part)
    // Method to check if the medicine is expiring within a given number of days
    public boolean isExpiringSoon(int days) {
        if (expDate == null) {
            return false; // If no expiration date is set, consider it not expiring soon
        }
        LocalDate today = LocalDate.now();
        LocalDate expiration = expDate.toLocalDate();
        long daysUntilExpiration = ChronoUnit.DAYS.between(today, expiration);
        return daysUntilExpiration <= days && daysUntilExpiration >= 0;
    }


    //Should be Notify.(Notification.java = Sachintha Dilsara part)
    // Fast-moving medicine check based on moving_type and stock_quantity
    // Fast Moving alert if stock < 100
    // Slow Moving alert if stock < 10
    public boolean isFastMoving() {
        if( "FAST".equalsIgnoreCase(movingType)){
             return stockQuantity < 100;
        }
        else{
            return stockQuantity < 10;
        }
    }


    //Should be Notify.(OutOfStockException.java = Sachintha Dilsara part)
    // Method to check if the requested quantity would result in negative stock
    public boolean NegativeStockCheck(int requestedQty) {
        return (this.stockQuantity - requestedQty) < 0;
    }

    //Should be Notify.(OutOfStockException.java = Sachintha Dilsara part)
    // Method to check if the medicine is out of stock
    public boolean isOutOfStock() {
        return this.stockQuantity <= 0;
    }


    // Getters and Setters
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
        this.movingType = movingType;
    }
}