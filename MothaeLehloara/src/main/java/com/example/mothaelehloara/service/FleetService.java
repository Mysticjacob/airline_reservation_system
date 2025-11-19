package com.example.mothaelehloara.service;


import com.example.mothaelehloara.model.Fleet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FleetService {

    // --- SIMULATED DATABASE LOGIC (PLACEHOLDER) ---
    private List<Fleet> mockFleetData = new ArrayList<>();

    // Initialize mock data for demonstration
    public FleetService() {
        // Add some mock records
        mockFleetData.add(new Fleet("AI-EX-789", "20", "150", "Trent 700", "880 km/h", "54.4 m", "60.3 m"));
        mockFleetData.add(new Fleet("IG-654", "30", "120", "CFM56-5B", "850 km/h", "39.5 m", "34.1 m"));
    }

    public List<Fleet> getAllFleets() throws SQLException {
        // Simulate fetching from database
        return new ArrayList<>(mockFleetData);
    }

    public Fleet getFleetByNumber(String noAircraft) throws SQLException {
        // Simulate fetching by number
        for (Fleet fleet : mockFleetData) {
            if (fleet.getNoAircraft().equals(noAircraft)) {
                return fleet;
            }
        }
        return null; // Not found
    }

    public void addFleet(Fleet fleet) throws SQLException {
        // Simulate adding to database
        mockFleetData.add(fleet);
        System.out.println("Fleet added to mock database: " + fleet.getNoAircraft());
    }

    public void updateFleet(Fleet fleet) throws SQLException {
        // Simulate updating in database
        for (int i = 0; i < mockFleetData.size(); i++) {
            if (mockFleetData.get(i).getNoAircraft().equals(fleet.getNoAircraft())) {
                mockFleetData.set(i, fleet);
                System.out.println("Fleet updated in mock database: " + fleet.getNoAircraft());
                return;
            }
        }
        System.out.println("Fleet with number " + fleet.getNoAircraft() + " not found for update.");
    }

    public void deleteFleet(String noAircraft) throws SQLException {
        // Simulate deleting from database
        mockFleetData.removeIf(fleet -> fleet.getNoAircraft().equals(noAircraft));
        System.out.println("Fleet with number " + noAircraft + " deleted from mock database.");
    }
}
