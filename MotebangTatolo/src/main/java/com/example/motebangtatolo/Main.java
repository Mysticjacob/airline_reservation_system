// File: src/main/java/com/example/motebangtatolo/Main.java

package com.example.motebangtatolo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // --- Load Customer Details first as per the PDF workflow ---
        // Load the customer_details.fxml file for Member 2's feature
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/motebangtatolo/view/customer_details.fxml"));
        Parent root = fxmlLoader.load();

        // Create the scene
        Scene scene = new Scene(root, 600, 600); // Set size for customer details

        // Set up the primary stage
        primaryStage.setTitle("Airline Reservation System - Customer Details");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            // Example: Close database connection if Member 2 manages it initially
            // DatabaseConnection.closeConnection(); // If applicable
        });
        primaryStage.show();

        // --- CRITICAL: Get the loaded CustomerDetailsController and link it to the ReservationController ---
        // This simulates the "Proceed to Reservation" logic that would happen later.
        com.example.motebangtatolo.controller.CustomerDetailsController customerController = fxmlLoader.getController();
        customerController.setPrimaryStage(primaryStage); // Pass stage if needed for loading reservation later
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}