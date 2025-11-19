// File: src/main/java/com/example/bolalatsita/model/User.java

package com.example.bolalatsita.model;

/**
 * Model class representing a user for login/registration.
 * Maps to a potential 'users' table in the database.
 */
public class User {

    private int userId; // Primary Key (auto-generated)
    private String username;
    private String passwordHash; // Store the hashed password, never plain text
    private String email; // Optional
    private String role; // e.g., "Admin", "Customer" (default)

    // Default constructor
    public User() {
    }

    // Constructor without ID (for creation)
    public User(String username, String passwordHash, String email, String role) {
        this.username = username;
        this.passwordHash = passwordHash; // Should be a hash, not plain text
        this.email = email;
        this.role = role;
    }

    // Constructor with ID (for retrieval from DB)
    public User(int userId, String username, String passwordHash, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash; // Should be a hash, not plain text
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash; // Should be a hash
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}