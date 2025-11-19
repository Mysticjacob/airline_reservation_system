package com.example.selekanepentse.controller;

import com.example.selekanepentse.model.Flight;
import com.example.selekanepentse.service.FlightService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class FlightDetailsController {

    @FXML
    private TextField fNameField;
    @FXML
    private TextField fCodeField;
    @FXML
    private TextField cCodeField;
    @FXML
    private TextField tExeSeatNoField;
    @FXML
    private TextField tEcoSeatNoField;

    @FXML
    private TableView<Flight> flightTableView;
    @FXML
    private TableColumn<Flight, String> colFlightName;
    @FXML
    private TableColumn<Flight, Integer> colFlightCode;
    @FXML
    private TableColumn<Flight, String> colClassCode;
    @FXML
    private TableColumn<Flight, Integer> colExecSeats;
    @FXML
    private TableColumn<Flight, Integer> colEcoSeats;

    @FXML
    private Button btnFirst;
    @FXML
    private Button btnPrevious;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnLast;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSave;

    private final FlightService flightService = new FlightService(); // Use the service
    private ObservableList<Flight> flightList;
    private int currentIndex = -1; // For navigation

    @FXML
    private void initialize() {
        setupTableColumns();
        loadFlightData(); // Load data from service
        setupEventHandlers();
        updateNavigationButtons();
        updateInputFields(false); // Initially disable input fields
    }

    private void setupTableColumns() {
        colFlightName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        colFlightCode.setCellValueFactory(new PropertyValueFactory<>("fCode"));
        colClassCode.setCellValueFactory(new PropertyValueFactory<>("cCode"));
        colExecSeats.setCellValueFactory(new PropertyValueFactory<>("tExeSeatNo"));
        colEcoSeats.setCellValueFactory(new PropertyValueFactory<>("tEcoSeatNo"));
    }

    private void loadFlightData() {
        try {
            List<Flight> flights = flightService.getAllFlights(); // Assume this method exists in FlightService
            flightList = FXCollections.observableArrayList(flights);
            flightTableView.setItems(flightList);
            if (!flightList.isEmpty()) {
                currentIndex = 0;
                displayFlight(currentIndex);
            }
        } catch (Exception e) { // Catch specific exceptions like SQLException
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading flight  " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void setupEventHandlers() {
        btnFirst.setOnAction(this::onFirst);
        btnPrevious.setOnAction(this::onPrevious);
        btnNext.setOnAction(this::onNext);
        btnLast.setOnAction(this::onLast);
        btnAdd.setOnAction(this::onAdd);
        btnNew.setOnAction(this::onNew);
        btnUpdate.setOnAction(this::onUpdate);
        btnDelete.setOnAction(this::onDelete);
        btnSave.setOnAction(this::onSave);
        // Add selection listener to table to update fields when a row is selected
        flightTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentIndex = flightList.indexOf(newSelection);
                displayFlight(currentIndex);
                updateNavigationButtons();
                updateInputFields(true); // Enable fields when a row is selected
            }
        });
    }

    private void displayFlight(int index) {
        if (index >= 0 && index < flightList.size()) {
            Flight flight = flightList.get(index);
            fNameField.setText(flight.getFName());
            fCodeField.setText(String.valueOf(flight.getFCode()));
            cCodeField.setText(flight.getCCode());
            tExeSeatNoField.setText(String.valueOf(flight.getTExeSeatNo()));
            tEcoSeatNoField.setText(String.valueOf(flight.getTEcoSeatNo()));
        }
    }

    private void updateNavigationButtons() {
        if (flightList.isEmpty()) {
            btnFirst.setDisable(true);
            btnPrevious.setDisable(true);
            btnNext.setDisable(true);
            btnLast.setDisable(true);
        } else {
            btnFirst.setDisable(currentIndex <= 0);
            btnPrevious.setDisable(currentIndex <= 0);
            btnNext.setDisable(currentIndex >= flightList.size() - 1);
            btnLast.setDisable(currentIndex >= flightList.size() - 1);
        }
    }

    private void updateInputFields(boolean enabled) {
        fNameField.setDisable(!enabled);
        fCodeField.setDisable(!enabled);
        cCodeField.setDisable(!enabled);
        tExeSeatNoField.setDisable(!enabled);
        tEcoSeatNoField.setDisable(!enabled);
        btnUpdate.setDisable(!enabled);
        btnDelete.setDisable(!enabled);
        // Add/Save might be enabled/disabled based on state
        btnAdd.setDisable(enabled);
        btnSave.setDisable(!enabled);
    }

    // Navigation methods
    private void onFirst(ActionEvent event) {
        currentIndex = 0;
        displayFlight(currentIndex);
        updateNavigationButtons();
    }

    private void onPrevious(ActionEvent event) {
        if (currentIndex > 0) {
            currentIndex--;
            displayFlight(currentIndex);
            updateNavigationButtons();
        }
    }

    private void onNext(ActionEvent event) {
        if (currentIndex < flightList.size() - 1) {
            currentIndex++;
            displayFlight(currentIndex);
            updateNavigationButtons();
        }
    }

    private void onLast(ActionEvent event) {
        currentIndex = flightList.size() - 1;
        displayFlight(currentIndex);
        updateNavigationButtons();
    }

    // CRUD methods
    private void onAdd(ActionEvent event) {
        // Clear fields and prepare for adding new record
        fNameField.clear();
        fCodeField.clear();
        cCodeField.clear();
        tExeSeatNoField.clear();
        tEcoSeatNoField.clear();
        updateInputFields(true); // Enable fields for input
        // Set focus on first field or disable Add button until ready
        btnAdd.setDisable(true);
        btnSave.setDisable(false);
    }

    private void onNew(ActionEvent event) {
        // Similar to Add, clear fields
        onAdd(event);
    }

    private void onUpdate(ActionEvent event) {
        if (currentIndex >= 0) {
            Flight selectedFlight = flightList.get(currentIndex);
            // Update the selected object with values from fields
            selectedFlight.setFName(fNameField.getText());
            selectedFlight.setFCode(Integer.parseInt(fCodeField.getText()));
            selectedFlight.setCCode(cCodeField.getText());
            selectedFlight.setTExeSeatNo(Integer.parseInt(tExeSeatNoField.getText()));
            selectedFlight.setTEcoSeatNo(Integer.parseInt(tEcoSeatNoField.getText()));

            try {
                flightService.updateFlight(selectedFlight); // Assume this method exists
                // Refresh the table view
                loadFlightData();
                // Optionally, re-select the updated row
                currentIndex = flightList.indexOf(selectedFlight);
                displayFlight(currentIndex);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error updating flight: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void onDelete(ActionEvent event) {
        if (currentIndex >= 0) {
            Flight flightToDelete = flightList.get(currentIndex);
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete flight " + flightToDelete.getFName() + "?");
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        flightService.deleteFlight(flightToDelete.getFCode()); // Assume this method exists
                        loadFlightData(); // Refresh the list
                        if (!flightList.isEmpty()) {
                            currentIndex = Math.min(currentIndex, flightList.size() - 1);
                            displayFlight(currentIndex);
                        } else {
                            currentIndex = -1;
                            displayFlight(currentIndex);
                        }
                        updateNavigationButtons();
                        updateInputFields(false); // Disable fields if list is empty or no selection
                    } catch (Exception e) {
                        e.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Error deleting flight: " + e.getMessage());
                        alert.showAndWait();
                    }
                }
            });
        }
    }

    private void onSave(ActionEvent event) {
        // Validate input
        if (fNameField.getText().isEmpty() || fCodeField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Flight Name and Code are required.");
            alert.showAndWait();
            return;
        }

        Flight newFlight = new Flight();
        newFlight.setFName(fNameField.getText());
        newFlight.setFCode(Integer.parseInt(fCodeField.getText()));
        newFlight.setCCode(cCodeField.getText());
        newFlight.setTExeSeatNo(Integer.parseInt(tExeSeatNoField.getText()));
        newFlight.setTEcoSeatNo(Integer.parseInt(tEcoSeatNoField.getText()));

        try {
            flightService.addFlight(newFlight); // Assume this method exists
            loadFlightData(); // Refresh the list
            // Optionally, select the newly added flight
            // currentIndex = flightList.size() - 1; // Might not work immediately after refresh
            updateInputFields(false); // Disable fields after saving
            btnAdd.setDisable(false);
            btnSave.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error saving flight: " + e.getMessage());
            alert.showAndWait();
        }
    }
}