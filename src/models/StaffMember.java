package models;


// Base class for everyone who uses the system (Owner and Cashier).
public class StaffMember {
    private String staffId;      // O001 = owner, C001 = cashier
    private String userName;
    private String staffName;
    private String role;         // "OWNER" or "CASHIER"
    private String contactNumber;
    private String password;
    private String status;       // "ACTIVE" or "INACTIVE"

    public StaffMember() {
    }

    public StaffMember(String staffId, String userName, String staffName, String role,
                       String contactNumber, String password, String status) {
        this.staffId = staffId;
        this.userName = userName;
        this.staffName = staffName;
        this.role = role;
        this.contactNumber = contactNumber;
        this.password = password;
        this.status = status;
    }

    // ---------- Getters and setters ----------
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }

    public String getRole() { return role; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // ---------- Actions shared by Owner and Cashier ----------


}
