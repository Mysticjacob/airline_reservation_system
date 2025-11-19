// File: src/main/java/com/example/bolalatsita/controller/MainController.java

package com.example.bolalatsita.controller;

import com.example.bolalatsita.service.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException; // Import for exception handling if needed in MainController itself

public class MainController {

    // --- Menu Items ---
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

    // --- Statistics Labels (NEW - might be loaded later or updated by other controllers) ---
    // @FXML private Label availableAircraftLabel; // Example
    // @FXML private Label totalFlightsLabel; // Example
    // @FXML private Label totalBookingsLabel; // Example
    // @FXML private Label waitingListLabel; // Example

    // --- Services for fetching statistics (if loaded here) ---
    // private final FleetService fleetService = new FleetService(); // Example
    // private final FlightService flightService = new FlightService(); // Example
    // private final ReservationService reservationService = new ReservationService(); // Example

    private String userRole; // Store the logged-in user's role
    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void setUserRole(String role) {
        this.userRole = role;
        // Disable menu items based on role after the role is set
        updateMenuBasedOnRole();
        // Optionally load statistics here if this controller manages them
        // loadStatistics();
    }

    @FXML
    private void initialize() {
        // Set up event handlers for menu items
        menuExitItem.setOnAction(this::handleExit);
        menuFlightDetailsItem.setOnAction(this::handleFlightDetails);
        menuFleetInfoItem.setOnAction(this::handleFleetInfo);
        menuFareDetailsItem.setOnAction(this::handleFareDetails);
        menuNewReservationItem.setOnAction(this::handleNewReservation);
        menuCancelTicketItem.setOnAction(this::handleCancelTicket);
        menuShowTimingsItem.setOnAction(this::handleShowTimings);
        menuAboutItem.setOnAction(this::handleAbout);

        // Note: loadStatistics() is now called in setUserRole after the role is known
        // loadStatistics(); // Remove this call from initialize
    }

    private void updateMenuBasedOnRole() {
        if ("Customer".equalsIgnoreCase(userRole)) {
            // Disable admin-specific menu items for customers
            menuFleetInfoItem.setDisable(true);
            menuFareDetailsItem.setDisable(true);
            menuFlightDetailsItem.setDisable(true); // Or keep enabled if customers can view general flight info
            System.out.println("Customer access granted. Admin features disabled.");
        } else if ("Admin".equalsIgnoreCase(userRole)) {
            // Admin has access to everything
            menuFleetInfoItem.setDisable(false);
            menuFareDetailsItem.setDisable(false);
            menuFlightDetailsItem.setDisable(false);
            System.out.println("Admin access granted.");
        } else {
            // Fallback if role is not set or unknown
            System.err.println("Unknown or unset user role: " + userRole);
            // Disable sensitive features by default or show an error
            menuFleetInfoItem.setDisable(true);
            menuFareDetailsItem.setDisable(true);
            menuFlightDetailsItem.setDisable(true);
        }
    }

    // --- NEW: Method to load statistics from the database (example) ---
    // private void loadStatistics() {
    //     try {
    //         // Example: Get total number of aircraft from fleet_information
    //         int totalAircraft = fleetService.getAllFleets().size(); // Assuming this method exists in FleetService
    //         availableAircraftLabel.setText("Available Aircraft: " + totalAircraft);
    //
    //         // Example: Get total number of flights from flight_information
    //         int totalFlights = flightService.getAllFlights().size(); // Assuming this method exists in FlightService
    //         totalFlightsLabel.setText("Total Flights: " + totalFlights);
    //
    //         // Example: Get total number of confirmed bookings from passenger_record
    //         int totalBookings = reservationService.getConfirmedBookingCount(); // This method exists in the updated ReservationService
    //         totalBookingsLabel.setText("Total Bookings: " + totalBookings);
    //
    //         // Example: Get total number of waiting list passengers from passenger_record
    //         int waitingListCount = reservationService.getWaitingListCount(); // This method exists in the updated ReservationService
    //         waitingListLabel.setText("Waiting List: " + waitingListCount);
    //
    //     } catch (SQLException e) { // Catch specific SQLException
    //         e.printStackTrace();
    //         // Handle the exception gracefully, perhaps log it and show a default message
    //         System.err.println("Error loading statistics: " + e.getMessage());
    //         availableAircraftLabel.setText("Available Aircraft: Error");
    //         totalFlightsLabel.setText("Total Flights: Error");
    //         totalBookingsLabel.setText("Total Bookings: Error");
    //         waitingListLabel.setText("Waiting List: Error");
    //     } catch (Exception e) { // Catch any other general exceptions
    //         e.printStackTrace();
    //         System.err.println("Unexpected error loading statistics: " + e.getMessage());
    //         availableAircraftLabel.setText("Available Aircraft: Error");
    //         totalFlightsLabel.setText("Total Flights: Error");
    //         totalBookingsLabel.setText("Total Bookings: Error");
    //         waitingListLabel.setText("Waiting List: Error");
    //     }
    // }


    // --- Menu Item Handlers ---
    @FXML
    private void handleExit(ActionEvent event) {
        // Close the database connection before exiting
        DatabaseConnection.closeConnection();
        System.exit(0); // Or primaryStage.close() if you prefer
    }

    @FXML
    private void handleFlightDetails(ActionEvent event) {
        // Check role if needed, although updateMenuBasedOnRole should handle visibility/disabling
        if ("Customer".equalsIgnoreCase(userRole)) {
            System.out.println("Customer cannot access Flight Details.");
            // Optionally show an alert or do nothing
            return;
        }
        loadView("flight_details.fxml", "Flight Details");
    }

    @FXML
    private void handleFleetInfo(ActionEvent event) {
        if ("Customer".equalsIgnoreCase(userRole)) {
            System.out.println("Customer cannot access Fleet Information.");
            return;
        }
        loadView("fleet_information.fxml", "Fleet Information");
    }

    @FXML
    private void handleFareDetails(ActionEvent event) {
        if ("Customer".equalsIgnoreCase(userRole)) {
            System.out.println("Customer cannot access Fare Details.");
            return;
        }
        loadView("fare_details.fxml", "Fare Details");
    }

    @FXML
    private void handleNewReservation(ActionEvent event) {
        // This is customer-facing, allow for Customer role, check for Admin if needed
        // The logic for customer details controller injection remains handled by Member 2's flow
        loadView("reservation.fxml", "New Reservation"); // Removed customerController argument
    }

    @FXML
    private void handleCancelTicket(ActionEvent event) {
        // This is customer-facing, but potentially needs filtering by user ID later
        // Allow for both roles for now, but CancellationController needs user-specific logic
        if ("Customer".equalsIgnoreCase(userRole)) {
            // Load a cancellation view that filters by the current user
            loadView("cancellation.fxml", "Cancel My Tickets"); // Modify CancellationController logic
        } else { // Admin
            loadView("cancellation.fxml", "Cancel Ticket (All)"); // Admin can cancel any ticket
        }
    }

    @FXML
    private void handleShowTimings(ActionEvent event) {
        // This is customer-facing
        loadView("show_timings.fxml", "Show Timings (Delhi to Gau)");
    }

    @FXML
    private void handleAbout(ActionEvent event) {
        System.out.println("About Airline Reservation System clicked.");
    }

    // --- Modified loadView method (REMOVED CustomerDetailsController dependency) ---
    private void loadView(String fxmlFileName, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bolalatsita/view/" + fxmlFileName));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Apply global CSS if you have one
            // scene.getStylesheets().add(getClass().getResource("/com/airline/style.css").toExternalForm());

            Stage stage = new Stage(); // Create a new stage for each view
            stage.setTitle(title);
            stage.setScene(scene);

            // --- CRITICAL: Get the loaded ReservationController and inject CustomerDetailsController ---
            // This part is REMOVED from MainController as it's handled by Member 2's flow
            // if ("reservation.fxml".equals(fxmlFileName) && customerController != null) {
            //     ReservationController reservationController = loader.getController();
            //     reservationController.setCustomerDetailsController(customerController); // Inject the controller
            // }

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
            System.err.println("Error loading view: " + fxmlFileName);
        }
    }
}