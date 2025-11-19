// File: src/main/java/com/example/motebangtatolo/controller/MainController.java

package com.example.motebangtatolo.controller;

import com.example.motebangtatolo.service.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class MainController {

    @FXML
    private MenuItem menuExitItem;
    @FXML
    private MenuItem menuFlightDetailsItem;
    @FXML
    private MenuItem menuFleetInfoItem;
    @FXML
    private MenuItem menuFareDetailsItem;
    @FXML
    private MenuItem menuNewReservationItem;
    @FXML
    private MenuItem menuCancelTicketItem;
    @FXML
    private MenuItem menuShowTimingsItem;
    @FXML
    private MenuItem menuAboutItem;

    // --- Store reference to Customer Details Stage and Controller Instance ---
    private Stage customerDetailsStage;
    private CustomerDetailsController customerDetailsControllerInstance; // Store the instance

    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void initialize() {
        menuExitItem.setOnAction(this::handleExit);
        menuFlightDetailsItem.setOnAction(this::handleFlightDetails);
        menuFleetInfoItem.setOnAction(this::handleFleetInfo);
        menuFareDetailsItem.setOnAction(this::handleFareDetails);
        menuNewReservationItem.setOnAction(this::handleNewReservation);
        menuCancelTicketItem.setOnAction(this::handleCancelTicket);
        menuShowTimingsItem.setOnAction(this::handleShowTimings);
        menuAboutItem.setOnAction(this::handleAbout);
    }

    @FXML
    private void handleExit(ActionEvent event) {
        DatabaseConnection.closeConnection();
        System.exit(0); // Or primaryStage.close() if you prefer
    }

    @FXML
    private void handleFlightDetails(ActionEvent event) {
        loadView("flight_details.fxml", "Flight Details");
    }

    @FXML
    private void handleFleetInfo(ActionEvent event) {
        loadView("fleet_information.fxml", "Fleet Information");
    }

    @FXML
    private void handleFareDetails(ActionEvent event) {
        loadView("fare_details.fxml", "Fare Details");
    }

    @FXML
    private void handleNewReservation(ActionEvent event) {
        // --- CHECK IF CUSTOMER DETAILS CONTROLLER INSTANCE IS AVAILABLE ---
        // This handles the case where the user clicks the menu item *after* filling the customer details window
        // and clicking the "Proceed to Reservation" button within that window.
        if (customerDetailsControllerInstance == null) {
            System.out.println("Customer Details window is not open or available via menu click.");
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Please open the Customer Details form first, fill in the details, and click the 'Proceed to Reservation' button in that window.");
            alert.showAndWait();
            // Optionally, open the customer details window automatically here if needed
            // loadView("customer_details.fxml", "Customer Details", null);
            return;
        }

        // If the customer details controller instance is available, proceed to load the reservation view
        loadView("reservation.fxml", "New Reservation", customerDetailsControllerInstance); // Pass the instance
    }


    @FXML
    private void handleCancelTicket(ActionEvent event) {
        loadView("cancellation.fxml", "Cancel Ticket");
    }

    @FXML
    private void handleShowTimings(ActionEvent event) {
        loadView("show_timings.fxml", "Show Timings (Delhi to Gau)");
    }

    @FXML
    private void handleAbout(ActionEvent event) {
        System.out.println("About Airline Reservation System clicked.");
    }

    // --- Modified loadView method to accept CustomerDetailsController ---
    private void loadView(String fxmlFileName, String title) {
        // This is the original method for views that don't need the customer controller
        loadView(fxmlFileName, title, null);
    }

    private void loadView(String fxmlFileName, String title, CustomerDetailsController customerController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/motebangtatolo/view/" + fxmlFileName));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage(); // Create a new stage for each view
            stage.setTitle(title);
            stage.setScene(scene);

            // --- CRITICAL: Get the loaded ReservationController and inject CustomerDetailsController ---
            if ("reservation.fxml".equals(fxmlFileName) && customerController != null) {
                ReservationController reservationController = loader.getController();
                reservationController.setCustomerDetailsController(customerController); // Inject the controller
                System.out.println("CustomerDetailsController injected into ReservationController from MainController.");
            }

            // --- Store reference if loading Customer Details window ---
            if ("customer_details.fxml".equals(fxmlFileName)) {
                customerDetailsStage = stage;
                customerDetailsControllerInstance = (CustomerDetailsController) loader.getController(); // Store the instance
                // Store the FXMLLoader in the root's userData to retrieve later in handleNewReservation if needed
                root.setUserData(loader);
                System.out.println("CustomerDetailsController instance stored in MainController.");
            }

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading view: " + fxmlFileName);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading view: " + fxmlFileName + "\n" + e.getMessage());
            alert.showAndWait();
        }
    }
}