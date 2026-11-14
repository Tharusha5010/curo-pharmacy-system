package auth;



import models.StaffMember;
import javax.swing.*;
import java.awt.Component;



public class UserSession {
 
    // Hold the Current User Session
    private static StaffMember currentUser = null;

 
    public static void setCurrentUser(StaffMember user) {
        currentUser = user;
    }

 
    public static StaffMember getCurrentUser() {
        return currentUser;
    }

 
    // Check the Role of the Current User
    public static boolean isOwner() {
        return currentUser != null && "OWNER".equalsIgnoreCase(currentUser.getRole());
    }

 
    public static void logout() {
        currentUser = null;
    }

 
    // Request One-Time Owner Override
    public static StaffMember requestOwnerOverride(Component parent, String warningMessage) {
        Object[] options = {"Owner Override", "Skip / Cancel"};
        int choice = JOptionPane.showOptionDialog(
            parent,
            warningMessage + "\n\nDo you want an Owner to authorize this action?",
            "Access Denied - Owner Authorization Required",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            options,
            options[1]
        );

     
     
        if (choice != JOptionPane.YES_OPTION) {
            return null;
        }

        JTextField txtOwnerId = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        Object[] loginFields = {
            "Owner Staff ID:", txtOwnerId,
            "Owner Password:", txtPassword
        };

        int loginResult = JOptionPane.showConfirmDialog(
            parent,
            loginFields,
            "One-Time Owner Override",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );


     
        if (loginResult == JOptionPane.OK_OPTION) {
            try {
                UserDAO userDAO = new UserDAO();
                StaffMember owner = userDAO.verifyOwnerOverride(
                    txtOwnerId.getText(),
                    new String(txtPassword.getPassword())
                );

                if (owner != null) {
                    return owner;
                } else {
                    JOptionPane.showMessageDialog(
                        parent,
                        "Invalid Owner credentials or account is not an Owner!",
                        "Override Failed",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
             
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Error: " + e.getMessage());
            }
        }
     
        return null;
    }
}
