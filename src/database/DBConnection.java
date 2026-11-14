package database;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBConnection {

    private static final String URL = "jdbc:sqlserver://localhost\\SQLEXPRESS01;databaseName=curo_db;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "curoAdmin";
    private static final String PASSWORD = "ict25954";


    
    public static Connection getConnection() {
        
        Connection conn = null;
        
        try {
            
            // Load JDBC Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            // Get Connection 
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            
        } catch (ClassNotFoundException e) {
            
            System.err.println("JDBC Driver not found: ");
            
            e.printStackTrace();

            
        } catch (SQLException e) {
            
            System.err.println("Failed to establish database connection: ");
            
            e.printStackTrace();
        }

        
        return conn;
    }
}
