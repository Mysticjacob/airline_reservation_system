// File: src/main/java/com/example/bolalatsita/service/UserService.java

package com.example.bolalatsita.service;

import com.example.bolalatsita.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64; // For simple encoding, consider stronger hashing like BCrypt in production

public class UserService {

    // Simple hash function (USE STRONGER HASHING like BCrypt in production!)
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Handle error appropriately, maybe return null or throw a custom exception
            return null;
        }
    }

    /**
     * Authenticates a user based on username and password.
     * @param username The username entered by the user.
     * @param password The plain text password entered by the user.
     * @return The User object if authentication is successful, null otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public User authenticateUser(String username, String password) throws SQLException {
        String hashedPassword = hashPassword(password); // Hash the input password
        String sql = "SELECT user_id, username, password_hash, email, role FROM users WHERE username = ? AND password_hash = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, hashedPassword); // Compare with the hashed password in the DB

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // If a row is found, authentication is successful. Create and return the User object.
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    return user; // Authentication successful
                }
            }
        }
        return null; // Authentication failed
    }

    /**
     * Registers a new user.
     * @param username The desired username.
     * @param password The plain text password provided by the user.
     * @param email The optional email address.
     * @return True if registration is successful, false otherwise (e.g., username exists).
     * @throws SQLException If a database access error occurs.
     */
    public boolean registerUser(String username, String password, String email) throws SQLException {
        String hashedPassword = hashPassword(password); // Hash the password before storing
        String role = "Customer"; // Default role for new registrations

        // Check if username already exists
        if (getUserByUsername(username) != null) {
            System.out.println("Username already exists: " + username);
            return false; // Registration failed due to duplicate username
        }

        String sql = "INSERT INTO users (username, password_hash, email, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // RETURN_GENERATED_KEYS to get the new user_id

            stmt.setString(1, username);
            stmt.setString(2, hashedPassword); // Store the hashed password
            stmt.setString(3, email); // Can be null
            stmt.setString(4, role); // Default role

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Optionally, retrieve the generated user_id if needed
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newUserId = generatedKeys.getInt(1);
                        System.out.println("User registered successfully with ID: " + newUserId);
                    }
                }
                return true; // Registration successful
            }
        }
        return false; // Registration failed for unknown reason
    }

    /**
     * Retrieves a user by their username.
     * @param username The username to search for.
     * @return The User object if found, null otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT user_id, username, password_hash, email, role FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        }
        return null; // User not found
    }

    // --- Other potential methods ---
    // public List<User> getAllUsers() throws SQLException { ... }
    // public boolean updateUser(User user) throws SQLException { ... }
    // public boolean deleteUser(int userId) throws SQLException { ... }
}