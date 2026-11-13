package auth;

import models.StaffMember;

 //Hold the current User Session
public class UserSession {
   
    private static StaffMember currentUser;

    public static void setCurrentUser(StaffMember user) {
        currentUser = user;
    }

    public static StaffMember getCurrentUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }
}