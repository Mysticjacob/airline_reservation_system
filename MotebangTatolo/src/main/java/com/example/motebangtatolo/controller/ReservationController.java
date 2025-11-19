// File: src/main/java/com/example/motebangtatolo/controller/ReservationController.java

package com.example.motebangtatolo.controller;

import com.example.motebangtatolo.model.Customer;
import com.example.motebangtatolo.service.ReservationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationController {

    // --- FXML Fields (Inputs, Buttons, Table, Labels) ---
    @FXML
    private TextField resDateField;
    @FXML
    private TextField sourceField;
    @FXML
    private TextField destinationField;
    @FXML
    private ComboBox<String> classComboBox; // "Economic", "Executive"
    @FXML
    private ComboBox<String> seatTypeComboBox; // "Window", "Aisle", "Middle"
    @FXML
    private Button btnSearchFlights;
    @FXML
    private Button btnBookTicket;
    @FXML
    private ProgressIndicator searchProgressIndicator;

    @FXML
    private TableView<ReservationService.AvailableFlightInfo> availableFlightsTable;
    @FXML
    private TableColumn<ReservationService.AvailableFlightInfo, String> colAvailFCode;
    @FXML
    private TableColumn<ReservationService.AvailableFlightInfo, String> colAvailFName; // Note: Might show F_CODE if name not fetched
    @FXML
    private TableColumn<ReservationService.AvailableFlightInfo, String> colAvailDTime;
    @FXML
    private TableColumn<ReservationService.AvailableFlightInfo, String> colAvailATime;
    @FXML
    private TableColumn<ReservationService.AvailableFlightInfo, Integer> colAvailEcoSeats;
    @FXML
    private TableColumn<ReservationService.AvailableFlightInfo, Integer> colAvailExeSeats;
    @FXML
    private TableColumn<ReservationService.AvailableFlightInfo, Double> colAvailFare;

    @FXML
    private Label fareLabel;
    @FXML
    private Label finalFareLabel;
    @FXML
    private Label statusLabel;

    // --- Controller Instance from Customer Details Window ---
    private CustomerDetailsController customerDetailsController; // This needs to be injected

    private final ReservationService reservationService = new ReservationService();
    private ObservableList<ReservationService.AvailableFlightInfo> allAvailableFlightsList;
    private ObservableList<ReservationService.AvailableFlightInfo> filteredFlightsList;

    @FXML
    private void initialize() {
        allAvailableFlightsList = FXCollections.observableArrayList();
        filteredFlightsList = FXCollections.observableArrayList();
        availableFlightsTable.setItems(filteredFlightsList);

        setupTableColumns();
        setupComboBoxes();
        setupEventHandlers();
        btnBookTicket.setDisable(true);

        // Prompt for date and load all flights initially
        PromptForDateAndLoadAllFlights();
    }

    // Method for MainController (or whoever loads this) to pass the CustomerDetailsController instance
    public void setCustomerDetailsController(CustomerDetailsController controller) {
        this.customerDetailsController = controller;
    }

    // --- Other methods remain largely the same ---
    private void PromptForDateAndLoadAllFlights() {
        TextInputDialog dialog = new TextInputDialog("2025-11-15");
        dialog.setTitle("Select Travel Date");
        dialog.setHeaderText("Please enter the travel date to load available flights:");
        dialog.setContentText("Date (YYYY-MM-DD):");

        dialog.showAndWait().ifPresent(date -> {
            resDateField.setText(date);
            loadAllFlightsForDate(date);
        });
    }

    private void setupTableColumns() {
        // Use PropertyValueFactory to map direct properties of AvailableFlightInfo (assuming you added getters)
        colAvailFCode.setCellValueFactory(new PropertyValueFactory<>("fCode"));
        colAvailFName.setCellValueFactory(new PropertyValueFactory<>("fName")); // Might show F_CODE
        colAvailDTime.setCellValueFactory(new PropertyValueFactory<>("dTime"));
        colAvailATime.setCellValueFactory(new PropertyValueFactory<>("aTime"));
        colAvailEcoSeats.setCellValueFactory(new PropertyValueFactory<>("availableEcoSeats"));
        colAvailExeSeats.setCellValueFactory(new PropertyValueFactory<>("availableExeSeats"));
        colAvailFare.setCellValueFactory(new PropertyValueFactory<>("fareAmount"));
    }

    private void setupComboBoxes() {
        classComboBox.getItems().addAll("Economic", "Executive");
        seatTypeComboBox.getItems().addAll("Window", "Aisle", "Middle");
    }

    private void setupEventHandlers() {
        btnSearchFlights.setOnAction(this::onSearchFlights);
        btnBookTicket.setOnAction(this::onBookTicket);
        availableFlightsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnBookTicket.setDisable(newSelection == null);
            if (newSelection != null) {
                fareLabel.setText(String.format("%.2f", newSelection.getFare().getFare()));
                calculateFinalFare();
            }
        });

        resDateField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.equals(oldValue)) {
                loadAllFlightsForDate(newValue);
            }
        });
    }

    private void loadAllFlightsForDate(String date) {
        if (date.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Date is required to calculate availability.");
            alert.showAndWait();
            return;
        }

        searchProgressIndicator.setVisible(true);
        btnSearchFlights.setDisable(true);

        try {
            List<ReservationService.AvailableFlightInfo> allFlightsForDate = reservationService.getAllAvailableFlightsForDate(date);
            allAvailableFlightsList.setAll(allFlightsForDate);
            filteredFlightsList.setAll(allAvailableFlightsList); // Show all initially
        } catch (Exception e) { // Catch broader exceptions like SQLException
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading flights: " + e.getMessage());
            alert.showAndWait();
        } finally {
            searchProgressIndicator.setVisible(false);
            btnSearchFlights.setDisable(false);
        }
    }

    private void onSearchFlights(ActionEvent event) {
        String date = resDateField.getText(); // Validate date format
        String source = sourceField.getText();
        String destination = destinationField.getText();

        if (date.isEmpty()) { // Date is crucial for availability calculation
            Alert alert = new Alert(Alert.AlertType.WARNING, "Date is required to calculate availability.");
            alert.showAndWait();
            return;
        }

        if (source.isEmpty() && destination.isEmpty()) {
            loadAllFlightsForDate(date);
            return;
        }

        List<ReservationService.AvailableFlightInfo> filteredList = allAvailableFlightsList.stream()
                .filter(flightInfo -> {
                    boolean matchesSource = source.isEmpty() || flightInfo.getFare().getSPlace().equalsIgnoreCase(source);
                    boolean matchesDestination = destination.isEmpty() || flightInfo.getFare().getDPlace().equalsIgnoreCase(destination);
                    // Optionally filter out flights with no available seats if desired, or keep them with 0 availability shown
                    // boolean hasAvailability = flightInfo.getAvailableEcoSeats() > 0 || flightInfo.getAvailableExeSeats() > 0;
                    return matchesSource && matchesDestination; // && hasAvailability; // Uncomment if you want to hide 0 availability
                })
                .collect(Collectors.toList());

        filteredFlightsList.setAll(filteredList);
    }


    private void calculateFinalFare() {
        // Get selected flight info (which contains the Fare object)
        ReservationService.AvailableFlightInfo selectedFlightInfo = availableFlightsTable.getSelectionModel().getSelectedItem();
        if (selectedFlightInfo == null) return;

        // Get the Fare object from the selected info
        com.example.motebangtatolo.model.Fare selectedFare = selectedFlightInfo.getFare();
        double baseFare = selectedFare.getFare();
        double finalFare = baseFare;

        // Get customer details and concession type
        if (customerDetailsController != null) { // Check if controller is available
            Customer customer = customerDetailsController.getCustomerFromFields();
            if (customer != null) { // Ensure customer data was retrieved
                String concessionTypeString = customer.getConcession(); // e.g., "Student", "Senior Citizen", "Cancer Patient"
                // Calculate discount based on concession type string using service
                // The service now handles converting the string to the ConcessionType enum internally
                finalFare = reservationService.calculateFinalFare(baseFare, concessionTypeString); // Assume this method exists in ReservationService
            } else {
                System.err.println("CustomerDetailsController returned null customer data.");
                // Optionally, reset fare labels or show a warning
                fareLabel.setText("0.00");
                finalFareLabel.setText("0.00");
                return; // Don't proceed if customer data is null
            }
        } else {
            System.err.println("CustomerDetailsController not set for fare calculation!");
            // Optionally, reset fare labels or show a warning
            fareLabel.setText("0.00");
            finalFareLabel.setText("0.00");
            return; // Don't proceed if controller is not set
        }

        fareLabel.setText(String.format("%.2f", baseFare));
        finalFareLabel.setText(String.format("%.2f", finalFare));
    }

    private void onBookTicket(ActionEvent event) {
        ReservationService.AvailableFlightInfo selectedFlightInfo = availableFlightsTable.getSelectionModel().getSelectedItem(); // Changed type
        String selectedClass = classComboBox.getSelectionModel().getSelectedItem();
        String selectedSeatType = seatTypeComboBox.getSelectionModel().getSelectedItem();
        String travelDate = resDateField.getText(); // Validate

        if (selectedFlightInfo == null || selectedClass == null || selectedSeatType == null || travelDate.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a flight, class, seat type, and ensure date is filled.");
            alert.showAndWait();
            return;
        }

        // Ensure availability check happens here before booking attempt
        if (("Economic".equals(selectedClass) && selectedFlightInfo.getAvailableEcoSeats() <= 0) ||
                ("Executive".equals(selectedClass) && selectedFlightInfo.getAvailableExeSeats() <= 0)) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selected class has no available seats on this flight.");
            alert.showAndWait();
            return;
        }

        // Get the actual flight code from the nested Fare object
        String flightCode = selectedFlightInfo.getFare().getFCode();

        // Get customer details
        if (customerDetailsController == null) {
            // Handle error: Customer details controller not set
            System.err.println("CustomerDetailsController not set!");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Customer details are not available. Please ensure the Customer Details window is open and filled, and the reservation was initiated from there.");
            alert.showAndWait();
            return; // Exit booking if controller is not set
        }

        Customer customer = customerDetailsController.getCustomerFromFields();

        try {
            // Call service to book ticket using the flight code from the selected info
            com.example.motebangtatolo.model.ReservedSeat bookingResult = reservationService.bookTicket(customer, flightCode, selectedClass, selectedSeatType, travelDate); // Assume this method exists

            if (bookingResult != null) {
                String status = bookingResult.getWaitingNo() > 0 ? "Waiting List" : "Confirmed";
                statusLabel.setText(status);
                String message = status.equals("Confirmed") ?
                        "Ticket booked successfully! PNR: " + bookingResult.getPnrNumber() + ", Seat: " + bookingResult.getSeatNumber() :
                        "Ticket added to waiting list! List Number: " + bookingResult.getWaitingNo();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
                alert.showAndWait();

                // Optionally clear reservation fields or disable booking until new search
                // resDateField.clear(); sourceField.clear(); destinationField.clear();
                // availableFlightsTable.getItems().clear(); // Or just disable the table/booking button
                btnBookTicket.setDisable(true);
            } else {
                // This case might be handled within the service if booking fails
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to book ticket. No available seats or other error.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error booking ticket: " + e.getMessage());
            alert.showAndWait();
        }
    }
}