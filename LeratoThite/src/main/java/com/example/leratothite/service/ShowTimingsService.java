// File: src/main/java/com/example/leratothite/service/ShowTimingsService.java

package com.example.leratothite.service;

import com.example.leratothite.model.Timing;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShowTimingsService {

    // --- SIMULATED DATABASE LOGIC (PLACEHOLDER for standalone project) ---
    // In the combined project, this will use real JDBC calls to the PostgreSQL database.
    private List<Timing> mockTimingData = new ArrayList<>();

    public ShowTimingsService() {
        // Initialize mock data for demonstration
        mockTimingData.add(new Timing("DEL-GAU-01", "Delhi", null, "Gau", "08:00:00", "10:30:00", "789", "Eco", 5000.00));
        mockTimingData.add(new Timing("DEL-GAU-02", "Delhi", "Kolkata", "Gau", "12:00:00", "15:00:00", "654", "Exe", 8000.00));
        mockTimingData.add(new Timing("MUM-CHN-01", "Mumbai", null, "Chennai", "10:00:00", "12:30:00", "321", "Eco", 4500.00)); // Example for other routes
    }

    /**
     * Retrieves flight timings based on source and destination.
     * In the combined project, this will query the FARE table.
     */
    public List<Timing> getTimingsByRoute(String source, String destination) throws SQLException {
        // Simulate database query by filtering mock data
        List<Timing> filteredTimings = new ArrayList<>();
        for (Timing timing : mockTimingData) {
            if (timing.getSPlace().equalsIgnoreCase(source) && timing.getDPlace().equalsIgnoreCase(destination)) {
                filteredTimings.add(timing);
            }
        }
        return filteredTimings;
    }

    // --- POTENTIAL METHOD: Calculate Fare based on concession ---
    // This logic might also reside in a shared ReservationService,
    // but if Member 5 is specifically handling concession input/display related to timings/fare lookup,
    // this method could be relevant here.
    // For now, let's assume it's a shared utility in ReservationService or a dedicated FareCalculationService.
    /*
    public double calculateFinalFare(double baseFare, String concessionTypeString) {
        ConcessionType concessionType = ConcessionType.fromString(concessionTypeString);
        double discountRate = concessionType.getDiscountRate(); // Assume ConcessionType enum has this
        return baseFare * (1 - discountRate);
    }
    */
}