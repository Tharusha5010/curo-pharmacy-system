package inventory;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Medicine;

public class InventoryDAO {


    // Inserts a new medicine batch into inventory table.
    // medicine_id is auto-generated through IDENTITY(1,1).
    public boolean addMedicine(Medicine med) {
        String query = "INSERT INTO inventory (medicine_name, company_name, batch_number, unit_price, stock_quantity, stock_date, exp_date, requires_prescription, moving_type) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, med.getMedicineName());
            stmt.setString(2, med.getCompanyName());
            stmt.setString(3, med.getBatchNumber());
            stmt.setDouble(4, med.getUnitPrice());
            stmt.setInt(5, med.getStockQuantity());
            stmt.setDate(6, med.getStockDate());
            stmt.setDate(7, med.getExpDate());
            stmt.setBoolean(8, med.isRequiresPrescription());
            stmt.setString(9, med.getMovingType());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error adding medicine record: " + e.getMessage());
            return false;
        }
    }


    // Updates stock quantity for a specific medicine record by medicine_id.
    public boolean updateQuantity(int medicineId, int newQty) {
        String query = "UPDATE inventory SET stock_quantity = ? WHERE medicine_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, newQty);
            stmt.setInt(2, medicineId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("Error updating quantity: " + e.getMessage());
            return false;
        }
    }


    // Deletes an inventory record by medicine_id.
    public boolean deleteMedicine(int medicineId) {
        String query = "DELETE FROM inventory WHERE medicine_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, medicineId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting medicine: " + e.getMessage());
            return false;
        }
    }


    // Helper method to map a ResultSet row to a Medicine object.
    private Medicine mapResultSetToMedicine(ResultSet rs) throws SQLException {
        Medicine med = new Medicine();
        med.setMedicineId(rs.getInt("medicine_id"));
        med.setMedicineName(rs.getString("medicine_name"));
        med.setCompanyName(rs.getString("company_name"));
        med.setBatchNumber(rs.getString("batch_number"));
        med.setUnitPrice(rs.getDouble("unit_price"));
        med.setStockQuantity(rs.getInt("stock_quantity"));
        med.setStockDate(rs.getDate("stock_date"));
        med.setExpDate(rs.getDate("exp_date"));
        med.setRequiresPrescription(rs.getBoolean("requires_prescription"));
        med.setMovingType(rs.getString("moving_type"));
        return med;
    }


    // Dispaly all inventory records from MSSQL. Getting all records.
    public List<Medicine> getAllMedicines() {
        List<Medicine> list = new ArrayList<>();
        String query = "SELECT * FROM inventory";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToMedicine(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching inventory items: " + e.getMessage());
        }

        return list;
    }


    // Live search by medicine name or batch number for auto-suggest support.
    public List<Medicine> searchMedicines(String keyword) {
        List<Medicine> medicineList = new ArrayList<>();
        String query = "SELECT * FROM inventory WHERE medicine_name LIKE ? OR batch_number LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String searchPattern = keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Medicine med = new Medicine();
                    med.setMedicineId(rs.getInt("medicine_id"));
                    med.setMedicineName(rs.getString("medicine_name"));
                    med.setCompanyName(rs.getString("company_name"));
                    med.setBatchNumber(rs.getString("batch_number"));
                    med.setUnitPrice(rs.getDouble("unit_price"));
                    med.setStockQuantity(rs.getInt("stock_quantity"));
                    med.setStockDate(rs.getDate("stock_date"));
                    med.setExpDate(rs.getDate("exp_date"));
                    med.setRequiresPrescription(rs.getBoolean("requires_prescription"));
                    med.setMovingType(rs.getString("moving_type"));

                    medicineList.add(med);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error searching medicine records: " + e.getMessage());
        }

        return medicineList;
    }


    //Sachintha Dilsara's part please check
    // (Connect with Notification.java)
    // Display medicines with stock quantity below or equal to a given limits .
    public List<Medicine> getLowStockMedicines(int threshold) {
        List<Medicine> list = new ArrayList<>();
        String query = "SELECT * FROM inventory WHERE stock_quantity <= ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, threshold);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToMedicine(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching low stock medicines: " + e.getMessage());
        }

        return list;
    }


    //Sachintha Dilsara's part please check
    // (Connect with Notification.java)
    // Display medicines that have expired relative to current date.
    public List<Medicine> getExpiredMedicines() {
        List<Medicine> list = new ArrayList<>();
        String query = "SELECT * FROM inventory WHERE exp_date <= GETDATE()";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToMedicine(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching expired medicines: " + e.getMessage());
        }

        return list;
    }
}