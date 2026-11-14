package alerts;

import database.DBConnection;
import models.Notification;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotificationManagaer {

    private static final List<Notification> inMemoryAlerts = new ArrayList<>();

    // Fetches live alerts from SQL Server inventory table + dynamic session alerts
    public List<Notification> fetchLiveNotifications() {
        List<Notification> alerts = new ArrayList<>(inMemoryAlerts);

        String sql = "SELECT medicine_name, batch_number, stock_quantity, exp_date FROM inventory " +
                     "WHERE stock_quantity < 10 OR exp_date <= DATEADD(day, 30, CAST(GETDATE() AS DATE))";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            LocalDate today = LocalDate.now();

            while (rs.next()) {
                String name = rs.getString("medicine_name");
                String batch = rs.getString("batch_number");
                int qty = rs.getInt("stock_quantity");
                LocalDate expDate = rs.getDate("exp_date").toLocalDate();

                if (qty == 0) {
                    alerts.add(new Notification("OUT OF STOCK", name, "Batch " + batch + " is completely out of stock!"));
                } else if (qty < 10) {
                    alerts.add(new Notification("LOW STOCK", name, "Stock is low (" + qty + " units remaining)."));
                }

                if (expDate.isBefore(today)) {
                    alerts.add(new Notification("EXPIRED", name, "Batch " + batch + " expired on " + expDate + "!"));
                } else if (!expDate.isAfter(today.plusDays(30))) {
                    alerts.add(new Notification("EXPIRING SOON", name, "Batch " + batch + " expires on " + expDate + "."));
                }
            }
        } catch (Exception e) {
            System.err.println("NotificationManagaer DB Check Info: " + e.getMessage());
        }

        return alerts;
    }

    // Add dynamic alert to session list
    public static void addDynamicAlert(String type, String title, String message) {
        inMemoryAlerts.add(0, new Notification(type, title, message));
    }

    // Open the live bottom-right notification popup window
    public static void showAlertsPopup() {
        SwingUtilities.invokeLater(() -> new AlertsFrame().setVisible(true));
    }

    // Trigger custom notification and optionally pop up AlertsFrame
    public static void triggerAlert(String type, String title, String message, boolean openPopup) {
        addDynamicAlert(type, title, message);
        if (openPopup) {
            showAlertsPopup();
        }
    }

    // Standard Static Dialog Alerts
    public static void showLoginSuccess(Component parent, String staffName, String role) {
        JOptionPane.showMessageDialog(parent, "Welcome, " + staffName + "!\nLogged in as: " + role, "Login Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showLoginFailed(Component parent) {
        JOptionPane.showMessageDialog(parent, "Invalid or Inactive Staff Credentials!", "Access Denied", JOptionPane.ERROR_MESSAGE);
    }

    public static void showUnauthorizedAlert(Component parent, String featureName) {
        JOptionPane.showMessageDialog(parent, "Access Denied! Requires OWNER privileges to access: " + featureName, "Unauthorized Access", JOptionPane.WARNING_MESSAGE);
    }

    public static void showBillSuccess(Component parent, int billId, double total) {
        JOptionPane.showMessageDialog(parent, "Bill #" + billId + " completed.\nNet Total: Rs. " + String.format("%.2f", total), "Billing Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // Dynamic Event Logging Helpers for Auth, Inventory, and Billing
    public static void notifyLoginSuccess(String staffId, String role) {
        addDynamicAlert("LOGIN", "Staff Login", "Staff member [" + staffId + "] logged in as " + role + ".");
    }

    public static void notifyLoginFailed(String staffId) {
        addDynamicAlert("SECURITY", "Failed Login", "Failed login attempt for Staff ID: " + staffId);
    }

    public static void notifyStockAdded(String medicineName, int qtyAdded, int newTotal) {
        addDynamicAlert("INVENTORY", medicineName, "Stock updated: +" + qtyAdded + " units added (Total: " + newTotal + ").");
    }

    public static void notifyInvalidAction(String staffId, String actionName, String reason) {
        addDynamicAlert("WARNING", actionName, "Blocked for [" + staffId + "]: " + reason);
    }

    // Exception Warning Helpers
    public static void handleExpiredMedicineWarning(Component parent, String medicineName, String expDate) {
        String msg = "Medicine '" + medicineName + "' expired on " + expDate + " and cannot be issued.";
        addDynamicAlert("EXPIRED", medicineName, msg);
        JOptionPane.showMessageDialog(parent, msg, "Expired Medicine Alert", JOptionPane.ERROR_MESSAGE);
    }

    public static void handleOutOfStockWarning(Component parent, String medicineName, int availableQty) {
        String msg = "Insufficient stock for '" + medicineName + "'. Available: " + availableQty;
        addDynamicAlert("OUT OF STOCK", medicineName, msg);
        JOptionPane.showMessageDialog(parent, msg, "Out of Stock Alert", JOptionPane.WARNING_MESSAGE);
    }

    public static void handleUnauthorizedWarning(Component parent, String staffId, String featureName) {
        notifyInvalidAction(staffId, featureName, "Requires OWNER privileges");
        showUnauthorizedAlert(parent, featureName);
    }
}