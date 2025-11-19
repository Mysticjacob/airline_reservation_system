// File: src/main/java/com/example/bolalatsita/controller/LoginController.java

package com.example.bolalatsita.controller;

import com.example.bolalatsita.model.User;
import com.example.bolalatsita.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label statusLabel;

    private final UserService userService = new UserService(); // Use the service
    private Stage primaryStage; // Reference to the main stage

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void initialize() {
        // Set up event handlers
        loginButton.setOnAction(this::handleLogin);
        registerButton.setOnAction(this::handleRegister);
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Username and Password are required.");
            return;
        }

        try {
            User authenticatedUser = userService.authenticateUser(username, password); // Call service method
            if (authenticatedUser != null) {
                statusLabel.setText("Login Successful!");
                statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
                // Load the main application window after successful login
                loadMainApplication(authenticatedUser.getRole()); // Pass user role if needed later
            } else {
                statusLabel.setText("Invalid Credentials");
                statusLabel.setTextFill(javafx.scene.paint.Color.RED);
                passwordField.clear(); // Clear password field on failure
            }
        } catch (SQLException e) { // Catch specific SQLException
            e.printStackTrace();
            statusLabel.setText("Error during authentication: " + e.getMessage());
            statusLabel.setTextFill(javafx.scene.paint.Color.RED);
        } catch (Exception e) { // Catch any other general exceptions
            e.printStackTrace();
            statusLabel.setText("Unexpected error during login: " + e.getMessage());
            statusLabel.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    private void loadMainApplication(String userRole) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bolalatsita/view/main.fxml"));
            Parent root = loader.load();

            // Get the MainController from the loader
            MainController mainController = loader.getController();
            // Pass the primary stage and user role to the main controller
            mainController.setPrimaryStage(primaryStage);
            mainController.setUserRole(userRole); // Pass the role to MainController for menu logic

            Scene scene = new Scene(root);

            // Apply global CSS if you have one
            // scene.getStylesheets().add(getClass().getResource("/com/airline/style.css").toExternalForm());

            primaryStage.setTitle("Airline Reservation System - Main Menu");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading main application: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        // Load the register.fxml view
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bolalatsita/view/register.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage registerStage = new Stage();
            registerStage.setTitle("Airline Reservation System - Register");
            registerStage.setScene(scene);

            // Pass the primary stage to the RegisterController if needed (e.g., to close login window)
            RegisterController registerController = loader.getController();
            registerController.setLoginStage(primaryStage); // Pass the login stage reference

            registerStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading registration view: " + e.getMessage());
            alert.showAndWait();
        }
    }
}