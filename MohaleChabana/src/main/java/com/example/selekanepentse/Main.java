package com.example.selekanepentse;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // For standalone demo, let's load the Flight Details view first
        // In the combined project, this might be a menu or loaded by MainController
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/selekanepentse/view/flight_details.fxml"));
        Parent root = fxmlLoader.load();

        // Create the scene
        Scene scene = new Scene(root, 800, 600); // Set size for data entry

        // Set up the primary stage
        primaryStage.setTitle("Airline Reservation System - Manage Flight Details");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            // Example: Close database connection if Member 4 manages it initially
            // DatabaseConnection.closeConnection(); // If applicable
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}