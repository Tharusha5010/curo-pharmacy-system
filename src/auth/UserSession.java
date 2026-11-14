package auth;



import models.StaffMember;



public class UserSession {
 
    //Hold the Current User Session
    private static StaffMember currentUser = null;

 

    public static void setCurrentUser(StaffMember user) {
        currentUser = user;
    }

 
    public static StaffMember getCurrentUser() {
        return currentUser;
    }

 
    //Check the Role of the Currunt User
    public static boolean isOwner() {
        return currentUser != null && "OWNER".equalsIgnoreCase(currentUser.getRole());
    }

 
    public static void logout() {
        currentUser = null;
    }
}
