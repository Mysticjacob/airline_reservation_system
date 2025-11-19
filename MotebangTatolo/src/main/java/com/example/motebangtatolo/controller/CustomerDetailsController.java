// File: src/main/java/com/example/motebangtatolo/controller/CustomerDetailsController.java

package com.example.motebangtatolo.controller;

import com.example.motebangtatolo.model.Customer;
import com.example.motebangtatolo.service.CustomerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerDetailsController {

    @FXML
    private TextField tDateField;
    @FXML
    private TextField custNameField;
    @FXML
    private TextField fatherNameField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField dobField; // Consider using DatePicker instead
    @FXML
    private TextField addressField;
    @FXML
    private TextField telNoField;
    @FXML
    private TextField professionField;
    @FXML
    private TextField securityField;
    @FXML
    private TextField concessionField; // e.g., "Student", "Senior Citizen", "Cancer Patient"
    // Add the button field
    @FXML
    private Button proceedToReservationButton; // Add the button field

    private final CustomerService customerService = new CustomerService();
    private Stage primaryStage; // Store the stage

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void initialize() {
        // Set up the action for the new button
        if (proceedToReservationButton != null) {
            proceedToReservationButton.setOnAction(this::handleProceedToReservation);
        }
    }

    // Add the action handler method
    @FXML
    private void handleProceedToReservation(ActionEvent event) {
        // Validate input (optional but recommended)
        if (custNameField.getText().isEmpty() || tDateField.getText().isEmpty()) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING, "Customer Name and Travel Date are required.");
            alert.showAndWait();
            return;
        }

        // The Customer details are already captured in the fields.
        // Load the Reservation window
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/motebangtatolo/view/reservation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("New Reservation");
            stage.setScene(scene);

            // --- CRITICAL: Get the ReservationController from the loader and inject this controller instance ---
            ReservationController reservationController = loader.getController();
            reservationController.setCustomerDetailsController(this); // Inject *this* controller instance

            stage.show();

            // Optionally, close the Customer Details window after proceeding
            Stage currentStage = (Stage) proceedToReservationButton.getScene().getWindow();
            currentStage.close(); // Closes the Customer Details window

        } catch (IOException e) {
            e.printStackTrace();
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR, "Error loading Reservation view: " + e.getMessage());
            alert.showAndWait();
        }
    }


    public Customer getCustomerFromFields() {
        Customer customer = new com.example.motebangtatolo.model.Customer();
        customer.setTDate(tDateField.getText()); // Validate date format
        customer.setCustName(custNameField.getText());
        customer.setFatherName(fatherNameField.getText());
        customer.setGender(genderField.getText());
        customer.setDob(dobField.getText()); // Validate date format
        customer.setAddress(addressField.getText());
        customer.setTelNo(Long.parseLong(telNoField.getText())); // Validate number format
        customer.setProfession(professionField.getText());
        customer.setSecurity(securityField.getText());
        customer.setConcession(concessionField.getText());
        return customer;
    }

    public void setCustomerToFields(Customer customer) {
        tDateField.setText(customer.getTDate());
        custNameField.setText(customer.getCustName());
        fatherNameField.setText(customer.getFatherName());
        genderField.setText(customer.getGender());
        dobField.setText(customer.getDob());
        addressField.setText(customer.getAddress());
        telNoField.setText(String.valueOf(customer.getTelNo()));
        professionField.setText(customer.getProfession());
        securityField.setText(customer.getSecurity());
        concessionField.setText(customer.getConcession());
    }

    public void clearFields() {
        tDateField.clear();
        custNameField.clear();
        fatherNameField.clear();
        genderField.clear();
        dobField.clear();
        addressField.clear();
        telNoField.clear();
        professionField.clear();
        securityField.clear();
        concessionField.clear();
    }

    // Example save method if used standalone
    @FXML
    private void handleSaveCustomer(ActionEvent event) {
        Customer customer = getCustomerFromFields();
        try {
            customerService.addCustomer(customer); // Assume this method exists
            System.out.println("Customer saved successfully.");
            clearFields(); // Optionally clear after saving
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error, e.g., show alert
        }
    }
}