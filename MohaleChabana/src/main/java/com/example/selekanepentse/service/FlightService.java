package com.example.selekanepentse.service;

import com.example.selekanepentse.model.Flight;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightService {

    // --- SIMULATED DATABASE LOGIC (PLACEHOLDER) ---
    private List<Flight> mockFlightData = new ArrayList<>();

    // Initialize mock data for demonstration
    public FlightService() {
        // Add some mock records
        mockFlightData.add(new Flight("Air India Express 789", 789, "Eco", 20, 150));
        mockFlightData.add(new Flight("IndiGo 654", 654, "Exe", 30, 120));
        mockFlightData.add(new Flight("SpiceJet 321", 321, "Eco", 15, 100));
    }

    public List<Flight> getAllFlights() throws SQLException {
        // Simulate fetching from database
        return new ArrayList<>(mockFlightData);
    }

    public Flight getFlightByCode(int fCode) throws SQLException {
        // Simulate fetching by code
        for (Flight flight : mockFlightData) {
            if (flight.getFCode() == fCode) {
                return flight;
            }
        }
        return null; // Not found
    }

    public void addFlight(Flight flight) throws SQLException {
        // Simulate adding to database
        mockFlightData.add(flight);
        System.out.println("Flight added to mock database: " + flight.getFName());
    }

    public void updateFlight(Flight flight) throws SQLException {
        // Simulate updating in database
        for (int i = 0; i < mockFlightData.size(); i++) {
            if (mockFlightData.get(i).getFCode() == flight.getFCode()) {
                mockFlightData.set(i, flight);
                System.out.println("Flight updated in mock database: " + flight.getFName());
                return;
            }
        }
        System.out.println("Flight with code " + flight.getFCode() + " not found for update.");
    }

    public void deleteFlight(int fCode) throws SQLException {
        // Simulate deleting from database
        mockFlightData.removeIf(flight -> flight.getFCode() == fCode);
        System.out.println("Flight with code " + fCode + " deleted from mock database.");
    }
}
