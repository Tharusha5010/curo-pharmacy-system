package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=curo_db;encrypt=true;trustServerCertificate=true";
    private static final String USER = "curoAdmin";
    private static final String PASSWORD = "ict25954";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }

        catch (ClassNotFoundException e) {
            System.err.println("MSSQL JDBC Driver not found. Add jar file to your lib folder: " + e.getMessage());
            throw new SQLException("SQL Server JDBC Driver not found.", e);
        } 
    }

}

