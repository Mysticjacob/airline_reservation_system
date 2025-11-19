package com.example.selekanepentse.service;

import com.example.selekanepentse.model.Fare;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FareService {

    // --- SIMULATED DATABASE LOGIC (PLACEHOLDER) ---
    private List<Fare> mockFareData = new ArrayList<>();

    // Initialize mock data for demonstration
    public FareService() {
        // Add some mock records
        mockFareData.add(new Fare("DEL-GAU-01", "Delhi", null, "Gau", "08:00:00", "10:30:00", "789", "Eco", 5000.00));
        mockFareData.add(new Fare("DEL-GAU-02", "Delhi", "Kolkata", "Gau", "12:00:00", "15:00:00", "654", "Exe", 8000.00));
    }

    public List<Fare> getAllFares() throws SQLException {
        // Simulate fetching from database
        return new ArrayList<>(mockFareData);
    }

    public Fare getFareByRouteCode(String routeCode) throws SQLException {
        // Simulate fetching by code
        for (Fare fare : mockFareData) {
            if (fare.getRouteCode().equals(routeCode)) {
                return fare;
            }
        }
        return null; // Not found
    }

    public void addFare(Fare fare) throws SQLException {
        // Simulate adding to database
        mockFareData.add(fare);
        System.out.println("Fare added to mock database: " + fare.getRouteCode());
    }

    public void updateFare(Fare fare) throws SQLException {
        // Simulate updating in database
        for (int i = 0; i < mockFareData.size(); i++) {
            if (mockFareData.get(i).getRouteCode().equals(fare.getRouteCode())) {
                mockFareData.set(i, fare);
                System.out.println("Fare updated in mock database: " + fare.getRouteCode());
                return;
            }
        }
        System.out.println("Fare with route code " + fare.getRouteCode() + " not found for update.");
    }

    public void deleteFare(String routeCode) throws SQLException {
        // Simulate deleting from database
        mockFareData.removeIf(fare -> fare.getRouteCode().equals(routeCode));
        System.out.println("Fare with route code " + routeCode + " deleted from mock database.");
    }
}