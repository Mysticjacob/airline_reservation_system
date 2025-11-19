// File: src/main/java/com/example/bolalatsita/controller/RegisterController.java

package com.example.bolalatsita.controller;

import com.example.bolalatsita.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField regUsernameField;
    @FXML
    private PasswordField regPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField emailField;
    @FXML
    private Button registerButton;
    @FXML
    private Button backToLoginButton;
    @FXML
    private Label regStatusLabel;

    private final UserService userService = new UserService(); // Use the service
    private Stage loginStage; // Reference to the login stage (to close it or show main)

    public void setLoginStage(Stage stage) {
        this.loginStage = stage;
    }

    @FXML
    private void initialize() {
        registerButton.setOnAction(this::handleRegister);
        backToLoginButton.setOnAction(this::handleBackToLogin);
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = regUsernameField.getText();
        String password = regPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String email = emailField.getText(); // Optional

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            regStatusLabel.setText("Username, Password, and Confirm Password are required.");
            regStatusLabel.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        if (!password.equals(confirmPassword)) {
            regStatusLabel.setText("Passwords do not match.");
            regStatusLabel.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        try {
            boolean success = userService.registerUser(username, password, email); // Call service method
            if (success) {
                regStatusLabel.setText("Registration Successful! Please log in.");
                regStatusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
                // Optionally clear fields
                regUsernameField.clear();
                regPasswordField.clear();
                confirmPasswordField.clear();
                emailField.clear();
            } else {
                regStatusLabel.setText("Registration Failed (Username might exist).");
                regStatusLabel.setTextFill(javafx.scene.paint.Color.RED);
            }
        } catch (SQLException e) { // Catch specific SQLException
            e.printStackTrace();
            regStatusLabel.setText("Error during registration: " + e.getMessage());
            regStatusLabel.setTextFill(javafx.scene.paint.Color.RED);
        } catch (Exception e) { // Catch any other general exceptions
            e.printStackTrace();
            regStatusLabel.setText("Unexpected error during registration: " + e.getMessage());
            regStatusLabel.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    @FXML
    private void handleBackToLogin(ActionEvent event) {
        // Close the registration stage
        Stage currentStage = (Stage) backToLoginButton.getScene().getWindow();
        currentStage.close();
    }
}