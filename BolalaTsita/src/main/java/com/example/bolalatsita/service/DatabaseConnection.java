// File: src/main/java/com/example/bolalatsita/service/DatabaseConnection.java

package com.example.bolalatsita.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // --- Configuration ---
    // Store credentials securely, e.g., in environment variables or a properties file
    // Example values (replace with your actual database details):
    // DB_URL: jdbc:postgresql://localhost:5432/airline_reservation_db
    // DB_USER: postgres
    // DB_PASSWORD: your_actual_password
    private static final String URL = System.getenv("DB_URL"); // e.g., "jdbc:postgresql://localhost:5432/airline_reservation_db"
    private static final String USER = System.getenv("DB_USER"); // e.g., "postgres"
    private static final String PASSWORD = System.getenv("DB_PASSWORD"); // e.g., "your_password"

    private static Connection connection;

    // Private constructor to prevent instantiation
    private DatabaseConnection() {}

    /**
     * Gets the database connection. If no connection exists or it's closed, it creates one.
     * @return A Connection object.
     * @throws SQLException if the connection cannot be established.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load the PostgreSQL JDBC driver (optional in newer JDBC versions)
                Class.forName("org.postgresql.Driver");

                // Establish the connection
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to the PostgreSQL database successfully.");
            } catch (ClassNotFoundException e) {
                System.err.println("PostgreSQL JDBC Driver not found.");
                throw new SQLException("JDBC Driver not found", e);
            }
        }
        return connection;
    }

    /**
     * Closes the database connection if it's open.
     * It's good practice to call this when the application shuts down.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
                connection = null; // Allow GC to clean up
            } catch (SQLException e) {
                System.err.println("Error closing connection.");
                e.printStackTrace();
            }
        }
    }
}