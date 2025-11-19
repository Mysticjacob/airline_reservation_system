// File: src/main/java/com/example/bolalatsita/Main.java

package com.example.bolalatsita;

import com.example.bolalatsita.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage; // Store the main stage

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;

        // Initially load the login screen
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/bolalatsita/view/login.fxml"));
        Parent root = fxmlLoader.load();

        // Get the controller instance from the FXMLLoader
        LoginController loginController = fxmlLoader.getController();
        // Pass the primary stage to the login controller so it can load main.fxml later
        loginController.setPrimaryStage(primaryStage);

        Scene scene = new Scene(root, 400, 300); // Set size for login

        primaryStage.setTitle("Airline Reservation System - Login");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            // Ensure database connection is closed when the main window closes
            com.example.bolalatsita.service.DatabaseConnection.closeConnection();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}