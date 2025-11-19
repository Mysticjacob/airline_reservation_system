// File: src/main/java/com/example/leratothite/controller/ShowTimingsController.java

package com.example.leratothite.controller;

import com.example.leratothite.model.Timing; // Assume a model class exists
import com.example.leratothite.service.ShowTimingsService; // Assume a service class exists
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ShowTimingsController {

    @FXML
    private TextField sourceField;
    @FXML
    private TextField destinationField;
    @FXML
    private Button btnShowTimings;
    @FXML
    private ProgressIndicator searchProgressIndicator;

    @FXML
    private TableView<Timing> timingsTable;
    @FXML
    private TableColumn<Timing, String> colFCode;
    @FXML
    private TableColumn<Timing, String> colFName; // Note: Might show F_CODE if name not fetched separately
    @FXML
    private TableColumn<Timing, String> colDTime;
    @FXML
    private TableColumn<Timing, String> colATime;
    @FXML
    private TableColumn<Timing, String> colClass; // Class code from FARE table
    @FXML
    private TableColumn<Timing, Double> colFare; // Fare from FARE table

    @FXML
    private Label statusLabel;

    private final ShowTimingsService showTimingsService = new ShowTimingsService(); // Use the service
    private ObservableList<Timing> timingsList;

    @FXML
    private void initialize() {
        setupTableColumns();
        setupEventHandlers();
        btnShowTimings.setDisable(true); // Initially disabled until fields are filled
        statusLabel.setText("Ready");

        // Add listeners to enable/disable the button based on input
        sourceField.textProperty().addListener((obs, oldVal, newVal) -> updateSearchButtonState());
        destinationField.textProperty().addListener((obs, oldVal, newVal) -> updateSearchButtonState());
    }

    private void updateSearchButtonState() {
        btnShowTimings.setDisable(sourceField.getText().trim().isEmpty() || destinationField.getText().trim().isEmpty());
    }

    private void setupTableColumns() {
        // Use PropertyValueFactory to map direct properties of Timing (assuming you added getters)
        // The Timing model should ideally contain fields from the FARE table relevant to timings.
        colFCode.setCellValueFactory(new PropertyValueFactory<>("fCode")); // Assumes getFCode() exists in Timing
        colFName.setCellValueFactory(new PropertyValueFactory<>("fName")); // Might show F_CODE if name not fetched separately
        colDTime.setCellValueFactory(new PropertyValueFactory<>("dTime")); // Assumes getDTime()
        colATime.setCellValueFactory(new PropertyValueFactory<>("aTime")); // Assumes getATime()
        colClass.setCellValueFactory(new PropertyValueFactory<>("cCode")); // Assumes getCCode() (class code)
        colFare.setCellValueFactory(new PropertyValueFactory<>("fare")); // Assumes getFare()
    }

    private void setupEventHandlers() {
        btnShowTimings.setOnAction(this::onShowTimings);
    }

    private void onShowTimings(ActionEvent event) {
        String source = sourceField.getText().trim(); // Get and trim the source from the text field
        String destination = destinationField.getText().trim(); // Get and trim the destination from the text field

        if (source.isEmpty() || destination.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Source and Destination are required.");
            alert.showAndWait();
            return;
        }

        searchProgressIndicator.setVisible(true); // Show indicator
        btnShowTimings.setDisable(true); // Disable button during search
        statusLabel.setText("Searching..."); // Update status

        try {
            // Call service method to get timings by route (source, destination)
            List<Timing> timings = showTimingsService.getTimingsByRoute(source, destination); // Assume this method exists in ShowTimingsService
            if (timings != null && !timings.isEmpty()) {
                timingsList = FXCollections.observableArrayList(timings);
                timingsTable.setItems(timingsList);
                statusLabel.setText("Found " + timings.size() + " flight(s)."); // Update status
            } else {
                timingsList = FXCollections.observableArrayList();
                timingsTable.setItems(timingsList);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No flights found for " + source + " to " + destination);
                alert.showAndWait();
                statusLabel.setText("No flights found for " + source + " to " + destination); // Update status
            }
        } catch (Exception e) { // Catch broader exceptions like SQLException
            e.printStackTrace();
            statusLabel.setText("Error: " + e.getMessage()); // Update status
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error fetching timings: " + e.getMessage());
            alert.showAndWait();
        } finally {
            searchProgressIndicator.setVisible(false); // Hide indicator
            btnShowTimings.setDisable(false); // Re-enable button
        }
    }
}