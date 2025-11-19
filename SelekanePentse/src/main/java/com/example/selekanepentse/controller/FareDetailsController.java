package com.example.selekanepentse.controller;


import com.example.selekanepentse.model.Fare;
import com.example.selekanepentse.service.FareService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class FareDetailsController {

    @FXML
    private TextField routeCodeField;
    @FXML
    private TextField sPlaceField;
    @FXML
    private TextField viaField;
    @FXML
    private TextField dPlaceField;
    @FXML
    private TextField dTimeField;
    @FXML
    private TextField aTimeField;
    @FXML
    private TextField fCodeField;
    @FXML
    private TextField cCodeField;
    @FXML
    private TextField fareField;

    @FXML
    private TableView<Fare> fareTableView;
    @FXML
    private TableColumn<Fare, String> colFareRouteCode;
    @FXML
    private TableColumn<Fare, String> colFareSPlace;
    @FXML
    private TableColumn<Fare, String> colFareDPlace;
    @FXML
    private TableColumn<Fare, String> colFareFCode;
    @FXML
    private TableColumn<Fare, String> colFareClass;
    @FXML
    private TableColumn<Fare, Double> colFareAmount;

    @FXML
    private Button btnAddFare;
    @FXML
    private Button btnUpdateFare;
    @FXML
    private Button btnDeleteFare;
    @FXML
    private Button btnSaveFare;

    private final FareService fareService = new FareService(); // Use the service
    private ObservableList<Fare> fareList;
    private int currentIndex = -1; // For navigation (if needed, similar to FlightDetails)

    @FXML
    private void initialize() {
        setupTableColumns();
        loadFareData(); // Load data from service
        setupEventHandlers();
        updateInputFields(false); // Initially disable input fields
    }

    private void setupTableColumns() {
        colFareRouteCode.setCellValueFactory(new PropertyValueFactory<>("routeCode"));
        colFareSPlace.setCellValueFactory(new PropertyValueFactory<>("sPlace"));
        colFareDPlace.setCellValueFactory(new PropertyValueFactory<>("dPlace"));
        colFareFCode.setCellValueFactory(new PropertyValueFactory<>("fCode"));
        colFareClass.setCellValueFactory(new PropertyValueFactory<>("cCode"));
        colFareAmount.setCellValueFactory(new PropertyValueFactory<>("fare"));
    }

    private void loadFareData() {
        try {
            List<Fare> fares = fareService.getAllFares(); // Assume this method exists in FareService
            fareList = FXCollections.observableArrayList(fares);
            fareTableView.setItems(fareList);
            if (!fareList.isEmpty()) {
                currentIndex = 0;
                displayFare(currentIndex);
            }
        } catch (Exception e) { // Catch specific exceptions like SQLException
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading fare  " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void setupEventHandlers() {
        btnAddFare.setOnAction(this::onAddFare);
        btnUpdateFare.setOnAction(this::onUpdateFare);
        btnDeleteFare.setOnAction(this::onDeleteFare);
        btnSaveFare.setOnAction(this::onSaveFare);
        // Add selection listener to table to update fields when a row is selected
        fareTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentIndex = fareList.indexOf(newSelection);
                displayFare(currentIndex);
                updateInputFields(true); // Enable fields when a row is selected
            }
        });
    }

    private void displayFare(int index) {
        if (index >= 0 && index < fareList.size()) {
            Fare fare = fareList.get(index);
            routeCodeField.setText(fare.getRouteCode());
            sPlaceField.setText(fare.getSPlace());
            viaField.setText(fare.getVia());
            dPlaceField.setText(fare.getDPlace());
            dTimeField.setText(fare.getDTime());
            aTimeField.setText(fare.getATime());
            fCodeField.setText(fare.getFCode());
            cCodeField.setText(fare.getCCode());
            fareField.setText(String.valueOf(fare.getFare()));
        }
    }

    private void updateInputFields(boolean enabled) {
        routeCodeField.setDisable(!enabled);
        sPlaceField.setDisable(!enabled);
        viaField.setDisable(!enabled);
        dPlaceField.setDisable(!enabled);
        dTimeField.setDisable(!enabled);
        aTimeField.setDisable(!enabled);
        fCodeField.setDisable(!enabled);
        cCodeField.setDisable(!enabled);
        fareField.setDisable(!enabled);
        btnUpdateFare.setDisable(!enabled);
        btnDeleteFare.setDisable(!enabled);
        btnAddFare.setDisable(enabled);
        btnSaveFare.setDisable(!enabled);
    }

    private void onAddFare(ActionEvent event) {
        routeCodeField.clear();
        sPlaceField.clear();
        viaField.clear();
        dPlaceField.clear();
        dTimeField.clear();
        aTimeField.clear();
        fCodeField.clear();
        cCodeField.clear();
        fareField.clear();
        updateInputFields(true);
        btnAddFare.setDisable(true);
        btnSaveFare.setDisable(false);
    }

    private void onUpdateFare(ActionEvent event) {
        if (currentIndex >= 0) {
            Fare selectedFare = fareList.get(currentIndex);
            selectedFare.setRouteCode(routeCodeField.getText());
            selectedFare.setSPlace(sPlaceField.getText());
            selectedFare.setVia(viaField.getText());
            selectedFare.setDPlace(dPlaceField.getText());
            selectedFare.setDTime(dTimeField.getText()); // Validate time format
            selectedFare.setATime(aTimeField.getText()); // Validate time format
            selectedFare.setFCode(fCodeField.getText());
            selectedFare.setCCode(cCodeField.getText());
            selectedFare.setFare(Double.parseDouble(fareField.getText())); // Validate number format

            try {
                fareService.updateFare(selectedFare); // Assume this method exists
                loadFareData(); // Refresh
                currentIndex = fareList.indexOf(selectedFare);
                displayFare(currentIndex);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error updating fare: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void onDeleteFare(ActionEvent event) {
        if (currentIndex >= 0) {
            Fare fareToDelete = fareList.get(currentIndex);
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete fare for route " + fareToDelete.getRouteCode() + "?");
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        fareService.deleteFare(fareToDelete.getRouteCode()); // Assume this method exists
                        loadFareData();
                        if (!fareList.isEmpty()) {
                            currentIndex = Math.min(currentIndex, fareList.size() - 1);
                            displayFare(currentIndex);
                        } else {
                            currentIndex = -1;
                            displayFare(currentIndex);
                        }
                        updateInputFields(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Error deleting fare: " + e.getMessage());
                        alert.showAndWait();
                    }
                }
            });
        }
    }

    private void onSaveFare(ActionEvent event) {
        if (routeCodeField.getText().isEmpty() || fCodeField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Route Code and Flight Code are required.");
            alert.showAndWait();
            return;
        }

        Fare newFare = new Fare();
        newFare.setRouteCode(routeCodeField.getText());
        newFare.setSPlace(sPlaceField.getText());
        newFare.setVia(viaField.getText());
        newFare.setDPlace(dPlaceField.getText());
        newFare.setDTime(dTimeField.getText()); // Validate time format
        newFare.setATime(aTimeField.getText()); // Validate time format
        newFare.setFCode(fCodeField.getText());
        newFare.setCCode(cCodeField.getText());
        newFare.setFare(Double.parseDouble(fareField.getText())); // Validate number format

        try {
            fareService.addFare(newFare); // Assume this method exists
            loadFareData();
            updateInputFields(false);
            btnAddFare.setDisable(false);
            btnSaveFare.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error saving fare: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
