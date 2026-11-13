package models;


//Declare Class and Encapsulated Variables
public class StaffMember {
    private String staffId;      
    private String staffName;
    private String role;          
    private String contactNumber;
    private String password;
    private String status;       

    // Object creation & data initialization
    public StaffMember() {
    }

    public StaffMember(String staffId, String userName, String staffName, String role,
                       String contactNumber, String password, String status) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.role = role;
        this.contactNumber = contactNumber;
        this.password = password;
        this.status = status;
    }

    //Reading and Updating private fields
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }

    public String getRole() { return role; }
    public void setRole(string role) { this.role = role }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }


}
