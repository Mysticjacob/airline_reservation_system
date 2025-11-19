package com.example.mothaelehloara.controller;


import com.example.mothaelehloara.model.Fleet;
import com.example.mothaelehloara.service.FleetService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class FleetInformationController {

    @FXML
    private TextField noAircraftField;
    @FXML
    private TextField clubPreCapacityField;
    @FXML
    private TextField ecoCapacityField;
    @FXML
    private TextField engineTypeField;
    @FXML
    private TextField cruiseSpeedField;
    @FXML
    private TextField airLengthField;
    @FXML
    private TextField wingSpanField;

    @FXML
    private TableView<Fleet> fleetTableView;
    @FXML
    private TableColumn<Fleet, String> colFleetNo;
    @FXML
    private TableColumn<Fleet, String> colFleetEcoCap;
    @FXML
    private TableColumn<Fleet, String> colFleetEngType;

    @FXML
    private Button btnAddFleet;
    @FXML
    private Button btnUpdateFleet;
    @FXML
    private Button btnDeleteFleet;
    @FXML
    private Button btnSaveFleet;

    private final FleetService fleetService = new FleetService(); // Use the service
    private ObservableList<Fleet> fleetList;
    private int currentIndex = -1; // For navigation (if needed, similar to FlightDetails)

    @FXML
    private void initialize() {
        setupTableColumns();
        loadFleetData(); // Load data from service
        setupEventHandlers();
        updateInputFields(false); // Initially disable input fields
    }

    private void setupTableColumns() {
        // Adjust Property names based on your Fleet model
        colFleetNo.setCellValueFactory(new PropertyValueFactory<>("noAircraft"));
        colFleetEcoCap.setCellValueFactory(new PropertyValueFactory<>("ecoCapacity")); // Assuming this is a string in the DB, adjust if numeric
        colFleetEngType.setCellValueFactory(new PropertyValueFactory<>("engineType"));
        // Add other columns as needed for full fleet details
    }

    private void loadFleetData() {
        try {
            List<Fleet> fleets = fleetService.getAllFleets(); // Assume this method exists in FleetService
            fleetList = FXCollections.observableArrayList(fleets);
            fleetTableView.setItems(fleetList);
            if (!fleetList.isEmpty()) {
                currentIndex = 0;
                displayFleet(currentIndex);
            }
        } catch (Exception e) { // Catch specific exceptions like SQLException
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading fleet data: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void setupEventHandlers() {
        btnAddFleet.setOnAction(this::onAddFleet);
        btnUpdateFleet.setOnAction(this::onUpdateFleet);
        btnDeleteFleet.setOnAction(this::onDeleteFleet);
        btnSaveFleet.setOnAction(this::onSaveFleet);
        // Add selection listener to table to update fields when a row is selected
        fleetTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentIndex = fleetList.indexOf(newSelection);
                displayFleet(currentIndex);
                updateInputFields(true); // Enable fields when a row is selected
            }
        });
    }

    private void displayFleet(int index) {
        if (index >= 0 && index < fleetList.size()) {
            Fleet fleet = fleetList.get(index);
            noAircraftField.setText(fleet.getNoAircraft());
            clubPreCapacityField.setText(fleet.getClubPreCapacity());
            ecoCapacityField.setText(fleet.getEcoCapacity());
            engineTypeField.setText(fleet.getEngineType());
            cruiseSpeedField.setText(fleet.getCruiseSpeed());
            airLengthField.setText(fleet.getAirLength());
            wingSpanField.setText(fleet.getWingSpan());
        }
    }

    private void updateInputFields(boolean enabled) {
        noAircraftField.setDisable(!enabled);
        clubPreCapacityField.setDisable(!enabled);
        ecoCapacityField.setDisable(!enabled);
        engineTypeField.setDisable(!enabled);
        cruiseSpeedField.setDisable(!enabled);
        airLengthField.setDisable(!enabled);
        wingSpanField.setDisable(!enabled);
        btnUpdateFleet.setDisable(!enabled);
        btnDeleteFleet.setDisable(!enabled);
        btnAddFleet.setDisable(enabled);
        btnSaveFleet.setDisable(!enabled);
    }

    private void onAddFleet(ActionEvent event) {
        noAircraftField.clear();
        clubPreCapacityField.clear();
        ecoCapacityField.clear();
        engineTypeField.clear();
        cruiseSpeedField.clear();
        airLengthField.clear();
        wingSpanField.clear();
        updateInputFields(true);
        btnAddFleet.setDisable(true);
        btnSaveFleet.setDisable(false);
    }

    private void onUpdateFleet(ActionEvent event) {
        if (currentIndex >= 0) {
            Fleet selectedFleet = fleetList.get(currentIndex);
            selectedFleet.setNoAircraft(noAircraftField.getText());
            selectedFleet.setClubPreCapacity(clubPreCapacityField.getText());
            selectedFleet.setEcoCapacity(ecoCapacityField.getText());
            selectedFleet.setEngineType(engineTypeField.getText());
            selectedFleet.setCruiseSpeed(cruiseSpeedField.getText());
            selectedFleet.setAirLength(airLengthField.getText());
            selectedFleet.setWingSpan(wingSpanField.getText());

            try {
                fleetService.updateFleet(selectedFleet); // Assume this method exists
                loadFleetData(); // Refresh
                currentIndex = fleetList.indexOf(selectedFleet);
                displayFleet(currentIndex);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error updating fleet: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void onDeleteFleet(ActionEvent event) {
        if (currentIndex >= 0) {
            Fleet fleetToDelete = fleetList.get(currentIndex);
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete fleet " + fleetToDelete.getNoAircraft() + "?");
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        fleetService.deleteFleet(fleetToDelete.getNoAircraft()); // Assume this method exists
                        loadFleetData();
                        if (!fleetList.isEmpty()) {
                            currentIndex = Math.min(currentIndex, fleetList.size() - 1);
                            displayFleet(currentIndex);
                        } else {
                            currentIndex = -1;
                            displayFleet(currentIndex);
                        }
                        updateInputFields(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Error deleting fleet: " + e.getMessage());
                        alert.showAndWait();
                    }
                }
            });
        }
    }

    private void onSaveFleet(ActionEvent event) {
        if (noAircraftField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Aircraft Number is required.");
            alert.showAndWait();
            return;
        }

        Fleet newFleet = new Fleet();
        newFleet.setNoAircraft(noAircraftField.getText());
        newFleet.setClubPreCapacity(clubPreCapacityField.getText());
        newFleet.setEcoCapacity(ecoCapacityField.getText());
        newFleet.setEngineType(engineTypeField.getText());
        newFleet.setCruiseSpeed(cruiseSpeedField.getText());
        newFleet.setAirLength(airLengthField.getText());
        newFleet.setWingSpan(wingSpanField.getText());

        try {
            fleetService.addFleet(newFleet); // Assume this method exists
            loadFleetData();
            updateInputFields(false);
            btnAddFleet.setDisable(false);
            btnSaveFleet.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error saving fleet: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
