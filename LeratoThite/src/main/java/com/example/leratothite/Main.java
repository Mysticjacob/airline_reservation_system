// File: src/main/java/com/example/leratothite/Main.java

package com.example.leratothite;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the show_timings.fxml file for Member 5's feature
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/leratothite/view/show_timings.fxml"));
        Parent root = fxmlLoader.load();

        // Create the scene
        Scene scene = new Scene(root, 800, 600); // Set size for show timings

        // Set up the primary stage
        primaryStage.setTitle("Airline Reservation System - Show Timings (Delhi to Gau)");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            // Example: Close database connection if Member 5 manages it initially
            // DatabaseConnection.closeConnection(); // If applicable
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}