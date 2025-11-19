// File: src/main/java/com/example/motebangtatolo/service/ReservationService.java

package com.example.motebangtatolo.service;

import com.example.motebangtatolo.model.*;
import com.example.motebangtatolo.model.enums.ConcessionType; // Import the enum
// Remove the import line if FlightClass was used: import com.example.motebangtatolo.model.enums.FlightClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReservationService {

    // ... (other service classes like CustomerService, FlightService, FareService remain the same if they exist) ...
    // private final CustomerService customerService = new CustomerService();
    // private final FlightService flightService = new FlightService();
    // private final FareService fareService = new FareService();
    private final Random random = new Random(); // For generating PNR

    // --- INNER CLASS FOR AVAILABILITY INFO (Assuming this exists) ---
    public static class AvailableFlightInfo {
        private Fare fare;
        private int availableEcoSeats;
        private int availableExeSeats;
        // Add other relevant fields if needed (e.g., totalEcoSeats, totalExeSeats)

        // Getters and Setters for AvailableFlightInfo
        public Fare getFare() {
            return fare;
        }

        public void setFare(Fare fare) {
            this.fare = fare;
        }

        public int getAvailableEcoSeats() {
            return availableEcoSeats;
        }

        public void setAvailableEcoSeats(int availableEcoSeats) {
            this.availableEcoSeats = availableEcoSeats;
        }

        public int getAvailableExeSeats() {
            return availableExeSeats;
        }

        public void setAvailableExeSeats(int availableExeSeats) {
            this.availableExeSeats = availableExeSeats;
        }

        // --- CRITICAL: Add direct getter methods for nested Fare properties ---
        // These are required for PropertyValueFactory in ReservationController
        public String getFCode() {
            return fare != null ? fare.getFCode() : "";
        }

        // Note on getFName(): The FARE table doesn't have F_NAME directly.
        // You might need to fetch it from FLIGHT_INFORMATION in the query within getAllAvailableFlightsForDate
        // and store the f_name in the tempFare object created there.
        // For now, returning the F_CODE as a placeholder or a derived name.
        public String getFName() {
            // Placeholder: Return F_CODE if no flight name is fetched separately
            // A proper implementation would involve joining FARE with FLIGHT_INFORMATION in the query
            // and storing the f_name in the tempFare object.
            return fare != null ? fare.getFCode() : ""; // Or get a proper name if available
        }

        public String getDTime() {
            return fare != null ? fare.getDTime() : "";
        }

        public String getATime() {
            return fare != null ? fare.getATime() : "";
        }

        public double getFareAmount() { // Renamed to avoid confusion with the Fare object
            return fare != null ? fare.getFare() : 0.0;
        }


        @Override
        public String toString() {
            return "AvailableFlightInfo{" +
                    "fare=" + fare +
                    ", availableEcoSeats=" + availableEcoSeats +
                    ", availableExeSeats=" + availableExeSeats +
                    '}';
        }
    }


    /**
     * Finds available flights based on date, source, and destination.
     * This method checks the FARE table for matching routes and then verifies
     * seat availability by comparing total seats (from FLIGHT_INFORMATION)
     * against reserved seats (from RESERVED_SEAT).
     */
    public List<AvailableFlightInfo> getAvailableFlights(String date, String source, String destination) throws SQLException {
        // Assuming a FareService exists to fetch from the FARE table
        // List<Fare> allMatchingFares = fareService.getTimingsByRoute(source, destination);
        // For this standalone example, let's simulate fetching from a database using raw JDBC calls.
        List<Fare> allMatchingFares = getFaresByRoute(source, destination); // Simulate fetching
        List<AvailableFlightInfo> availableFlights = new ArrayList<>();

        for (Fare fare : allMatchingFares) {
            String flightCode = fare.getFCode();
            String classCodeFromFare = fare.getCCode(); // Class code from the FARE table (e.g., "Eco", "Exe")

            // Get total seats from FLIGHT_INFORMATION using the flight code
            // Assuming a FlightService exists to fetch from the FLIGHT_INFORMATION table
            // Flight flight = flightService.getFlightByCode(Integer.parseInt(flightCode));
            Flight flight = getFlightByCode(Integer.parseInt(flightCode)); // Simulate fetching
            if (flight == null) {
                System.out.println("Warning: Flight code " + flightCode + " from FARE table not found in FLIGHT_INFORMATION.");
                continue; // Skip this fare if the flight doesn't exist
            }

            int totalEcoSeats = flight.getTEcoSeatNo();
            int totalExeSeats = flight.getTExeSeatNo();

            // Get reserved seats from RESERVED_SEAT table for this flight and date
            ReservedSeat reservationForDate = getReservationForFlightAndDate(flightCode, date);

            int reservedEcoSeats = (reservationForDate != null) ? reservationForDate.getTResEcoSeat() : 0;
            int reservedExeSeats = (reservationForDate != null) ? reservationForDate.getTResExeSeat() : 0;

            // Calculate available seats based on the class code from the FARE table
            int availableEcoSeats = 0;
            int availableExeSeats = 0;

            if ("Eco".equalsIgnoreCase(classCodeFromFare)) {
                availableEcoSeats = totalEcoSeats - reservedEcoSeats;
            } else if ("Exe".equalsIgnoreCase(classCodeFromFare)) {
                availableExeSeats = totalExeSeats - reservedExeSeats;
            } else {
                // Handle unexpected class code if necessary, or log a warning
                System.out.println("Warning: Unexpected class code '" + classCodeFromFare + "' for flight " + flightCode);
                continue; // Skip processing this fare
            }

            // Only add to available list if seats are available for the specific class in this fare record
            if (("Eco".equalsIgnoreCase(classCodeFromFare) && availableEcoSeats > 0) ||
                    ("Exe".equalsIgnoreCase(classCodeFromFare) && availableExeSeats > 0)) {

                // Create an object to hold both the Fare details and the calculated availability
                AvailableFlightInfo availableInfo = new AvailableFlightInfo();
                availableInfo.setFare(fare); // Set the original Fare object
                availableInfo.setAvailableEcoSeats(availableEcoSeats);
                availableInfo.setAvailableExeSeats(availableExeSeats);
                // You can add more fields like total seats if needed
                availableFlights.add(availableInfo);
            }
        }
        return availableFlights;
    }

    // --- SIMULATED DATABASE METHODS (PLACEHOLDER) ---
    // In the combined project, these would use actual JDBC calls via Service classes.
    private List<Fare> getFaresByRoute(String source, String destination) throws SQLException {
        List<Fare> fares = new ArrayList<>();
        // Simulate fetching from database
        // Example data based on the PDF tables and sample data
        if ("Delhi".equalsIgnoreCase(source) && "Gau".equalsIgnoreCase(destination)) {
            fares.add(createSampleFare("DEL-GAU-01", "Delhi", null, "Gau", "08:00:00", "10:30:00", "789", "Eco", 5000.00));
            fares.add(createSampleFare("DEL-GAU-02", "Delhi", "Kolkata", "Gau", "12:00:00", "15:00:00", "654", "Exe", 8000.00));
        }
        // Add more routes as needed for simulation
        return fares;
    }

    private Flight getFlightByCode(int fCode) throws SQLException {
        // Simulate fetching from database
        // Example data based on the PDF tables and sample data
        if (fCode == 789) {
            return new Flight("Air India Express 789", fCode, "Eco", 20, 150);
        } else if (fCode == 654) {
            return new Flight("IndiGo 654", fCode, "Exe", 30, 120);
        }
        // Add more flights as needed for simulation
        return null; // Not found
    }

    private ReservedSeat getReservationForFlightAndDate(String fCode, String tDate) throws SQLException {
        // Simulate fetching from database
        // Example: Assume 50 eco seats reserved for flight 789 on date 2025-11-15
        if ("789".equals(fCode) && "2025-11-15".equals(tDate)) {
            ReservedSeat reservation = new ReservedSeat();
            reservation.setFCode(fCode);
            reservation.setTResEcoSeat(50);
            reservation.setTResExeSeat(5);
            reservation.setTDate(tDate);
            reservation.setWaitingNo(0);
            return reservation;
        }
        // Add more simulation logic as needed
        return null; // No reservation found for this flight and date
    }

    private Fare createSampleFare(String routeCode, String sPlace, String via, String dPlace, String dTime, String aTime, String fCode, String cCode, double fare) {
        Fare fareObj = new Fare();
        fareObj.setRouteCode(routeCode);
        fareObj.setSPlace(sPlace);
        fareObj.setVia(via);
        fareObj.setDPlace(dPlace);
        fareObj.setDTime(dTime);
        fareObj.setATime(aTime);
        fareObj.setFCode(fCode);
        fareObj.setCCode(cCode);
        fareObj.setFare(fare);
        return fareObj;
    }
    // --- END SIMULATED DATABASE METHODS ---


    /**
     * Books a ticket for a customer.
     * Checks availability, updates reserved seats, creates a passenger record, and returns booking details (PNR, Seat No, Waiting List No).
     */
    public ReservedSeat bookTicket(Customer customer, String flightCode, String selectedClass, String seatType, String travelDate) throws SQLException {
        // FlightClass flightClass = FlightClass.fromString(selectedClass); // Use the enum if needed elsewhere

        // 1. Get total seats for the flight
        // Assuming FlightService exists
        // Flight flight = flightService.getFlightByCode(Integer.parseInt(flightCode));
        Flight flight = getFlightByCode(Integer.parseInt(flightCode)); // Simulate fetching
        if (flight == null) {
            throw new SQLException("Flight not found in FLIGHT_INFORMATION: " + flightCode);
        }
        int totalSeatsForClass = "Economic".equals(selectedClass) ? flight.getTEcoSeatNo() : flight.getTExeSeatNo(); // Use string comparison instead of enum for now

        // 2. Get reserved seats for this flight on the travel date
        ReservedSeat existingReservation = getReservationForFlightAndDate(flightCode, travelDate);

        int reservedSeatsForClass = 0;
        String classCodeForUpdate = null; // Used for updating the correct column in reserved_seat
        if ("Economic".equals(selectedClass)) { // Use string comparison
            reservedSeatsForClass = existingReservation != null ? existingReservation.getTResEcoSeat() : 0;
            classCodeForUpdate = "Eco"; // Or map to the code used in the DB column (e.g., "Eco")
        } else { // Executive
            reservedSeatsForClass = existingReservation != null ? existingReservation.getTResExeSeat() : 0;
            classCodeForUpdate = "Exe"; // Or map to the code used in the DB column (e.g., "Exe")
        }

        // 3. Check availability
        boolean hasAvailableSeat = reservedSeatsForClass < totalSeatsForClass;

        String pnrNumber = null;
        String seatNumber = null;
        int waitingNo = 0;
        String status = null; // 'Confirmed' or 'Waiting List'

        if (hasAvailableSeat) {
            // 4. Confirm seat - Assign a seat number (simple logic: next available)
            // In a real system, you'd have more complex seat selection (window, aisle, etc.)
            seatNumber = generateSeatNumber(selectedClass, reservedSeatsForClass + 1); // e.g., E1, E2, X1, X2
            pnrNumber = generatePNR(); // Generate unique PNR
            status = "Confirmed";

            // 5. Update the reserved_seat table
            // Check if a record exists for this flight and date
            if (existingReservation == null) {
                // Insert new record
                insertNewReservationRecord(flightCode, travelDate, classCodeForUpdate, 1); // 1 new seat reserved
            } else {
                // Update existing record: increment the count for the correct class
                updateExistingReservationRecord(flightCode, travelDate, classCodeForUpdate, reservedSeatsForClass + 1);
            }

            // 6. Add customer details to customer_details table (if not already present or if this is a new booking)
            // Note: This might need to link to the PNR or travel date
            // customerService.addCustomer(customer); // Be careful here, you might not want to add the same customer again.

        } else {
            // 8. No available seat - Add to waiting list
            waitingNo = getNextWaitingNumber(flightCode, travelDate, selectedClass); // Use string instead of enum
            pnrNumber = generatePNR(); // Generate unique PNR for waiting list entry too
            status = "Waiting List";
            // Optionally, update the reserved_seat table to increment the waiting_no count
            // Or, have a separate waiting_list table.
            // For simplicity here, we just return the waiting number.
        }

        // 7. CRITICAL: Add the reservation details to the PASSENGER_RECORD table (or equivalent)
        // This step is crucial but not explicitly defined in the name.PDF tables.
        // You might need to create a new table like:
        // CREATE TABLE PASSENGER_RECORD (pnr VARCHAR(10), cust_name VARCHAR(255), f_code VARCHAR(10), class VARCHAR(50), seat_no VARCHAR(10), travel_date DATE, status VARCHAR(20));
        // And insert the record here.
        // For now, we'll just return the booking result.
        insertPassengerRecord(pnrNumber, customer.getCustName(), flightCode, selectedClass, seatNumber, travelDate, status, waitingNo); // Assume this method exists

        return new ReservedSeat(flightCode, travelDate, waitingNo, pnrNumber, seatNumber);
    }

    private String generateSeatNumber(String classType, int seatIndex) {
        char prefix = classType.startsWith("Eco") ? 'E' : 'X';
        return prefix + String.format("%03d", seatIndex); // e.g., E001, E002, X001, X002
    }

    private String generatePNR() {
        // Simple PNR generation: 6-digit random number
        return String.format("%06d", random.nextInt(900000) + 100000);
    }

    private void insertNewReservationRecord(String fCode, String tDate, String classCode, int newReservedCount) throws SQLException {
        String sql = "INSERT INTO reserved_seat (f_code, t_res_eco_seat, t_res_exe_seat, t_date, waiting_no) VALUES (?, ?, ?, ?, 0)"; // Assuming initial waiting_no is 0
        int ecoCount = "Eco".equals(classCode) ? newReservedCount : 0;
        int exeCount = "Exe".equals(classCode) ? newReservedCount : 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fCode);
            stmt.setInt(2, ecoCount); // t_res_eco_seat
            stmt.setInt(3, exeCount); // t_res_exe_seat
            stmt.setString(4, tDate);
            // waiting_no is set to 0 initially
            stmt.executeUpdate();
        }
    }

    private void updateExistingReservationRecord(String fCode, String tDate, String classCode, int newReservedCount) throws SQLException {
        String sql;
        if ("Eco".equals(classCode)) {
            sql = "UPDATE reserved_seat SET t_res_eco_seat = ? WHERE f_code = ? AND t_date = ?";
        } else { // Exe
            sql = "UPDATE reserved_seat SET t_res_exe_seat = ? WHERE f_code = ? AND t_date = ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newReservedCount); // Set the correct count
            stmt.setString(2, fCode);
            stmt.setString(3, tDate);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to update reserved_seat for flight " + fCode + " on date " + tDate);
            }
        }
    }

    private int getNextWaitingNumber(String fCode, String tDate, String classType) throws SQLException {
        // Find the highest waiting number for this flight and date, then increment
        // This assumes waiting_no in reserved_seat represents the *highest* number assigned so far for waitlisted passengers on that flight/date.
        // A more robust system might use a separate WAITING_LIST table.
        String sql = "SELECT COALESCE(MAX(waiting_no), 0) + 1 AS next_waiting_no FROM reserved_seat WHERE f_code = ? AND t_date = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fCode);
            stmt.setString(2, tDate);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("next_waiting_no");
                }
            }
        }
        return 1; // Default if no records found for this flight/date
    }

    // --- NEW METHOD ADDED: Insert Booking Record into PASSENGER_RECORD table ---
    // This method needs to be implemented to store the final booking details.
    private void insertPassengerRecord(String pnr, String custName, String fCode, String classType, String seatNo, String tDate, String status, int waitingNo) throws SQLException {
        String sql = "INSERT INTO passenger_record (pnr, cust_name, f_code, class_type, seat_no, t_date, status, waiting_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pnr);
            stmt.setString(2, custName);
            stmt.setString(3, fCode);
            stmt.setString(4, classType);
            stmt.setString(5, seatNo); // Can be null if waiting list
            stmt.setString(6, tDate);
            stmt.setString(7, status);
            stmt.setInt(8, waitingNo);

            stmt.executeUpdate();
            System.out.println("Booking record inserted into passenger_record table for PNR: " + pnr);
        }
    }


    /**
     * Calculates the final fare based on the base fare and concession type string.
     * This method uses the ConcessionType enum.
     */
    public double calculateFinalFare(double baseFare, String concessionTypeString) {
        ConcessionType concessionType = ConcessionType.fromString(concessionTypeString); // Use the enum
        double discountRate = concessionType.getDiscountRate();
        return baseFare * (1 - discountRate);
    }


    /**
     * Fetches ALL available flights for a specific date from the FARE table,
     * calculates their availability against FLIGHT_INFORMATION and RESERVED_SEAT,
     * and returns them. This is used for the initial loading in the Reservation window.
     */
    public List<AvailableFlightInfo> getAllAvailableFlightsForDate(String date) throws SQLException {
        // Simulate fetching all fares
        // In a real system, you'd query the FARE table without source/destination filters initially
        // and then calculate availability based on date.
        List<Fare> allFares = getAllFares(); // Simulate fetching all fares
        List<AvailableFlightInfo> availableFlights = new ArrayList<>();

        for (Fare fare : allFares) {
            String flightCode = fare.getFCode();
            String classCodeFromFare = fare.getCCode(); // Class code from the FARE table (e.g., "Eco", "Exe")

            // Get total seats from FLIGHT_INFORMATION using the flight code
            Flight flight = getFlightByCode(Integer.parseInt(flightCode)); // Simulate fetching
            if (flight == null) {
                System.out.println("Warning: Flight code " + flightCode + " from FARE table not found in FLIGHT_INFORMATION.");
                continue; // Skip this fare if the flight doesn't exist
            }

            int totalEcoSeats = flight.getTEcoSeatNo();
            int totalExeSeats = flight.getTExeSeatNo();

            // Get reserved seats from RESERVED_SEAT table for this flight and date
            ReservedSeat reservationForDate = getReservationForFlightAndDate(flightCode, date);

            int reservedEcoSeats = (reservationForDate != null) ? reservationForDate.getTResEcoSeat() : 0;
            int reservedExeSeats = (reservationForDate != null) ? reservationForDate.getTResExeSeat() : 0;

            // Calculate available seats based on the class code from the FARE table
            int availableEcoSeats = 0;
            int availableExeSeats = 0;

            if ("Eco".equalsIgnoreCase(classCodeFromFare)) {
                availableEcoSeats = totalEcoSeats - reservedEcoSeats;
            } else if ("Exe".equalsIgnoreCase(classCodeFromFare)) {
                availableExeSeats = totalExeSeats - reservedExeSeats;
            } else {
                // Handle unexpected class code if necessary, or log a warning
                System.out.println("Warning: Unexpected class code '" + classCodeFromFare + "' for flight " + flightCode);
                continue; // Skip processing this fare
            }

            // Create an object to hold both the Fare details and the calculated availability
            // Add the record even if no seats are available (shows 0 availability)
            AvailableFlightInfo availableInfo = new AvailableFlightInfo();
            availableInfo.setFare(fare); // Set the original Fare object
            availableInfo.setAvailableEcoSeats(availableEcoSeats);
            availableInfo.setAvailableExeSeats(availableExeSeats);
            // You can add more fields like total seats if needed
            availableFlights.add(availableInfo);
        }
        return availableFlights;
    }

    // Simulated method to get all fares
    private List<Fare> getAllFares() throws SQLException {
        List<Fare> fares = new ArrayList<>();
        // Add sample data covering various routes
        fares.add(createSampleFare("DEL-GAU-01", "Delhi", null, "Gau", "08:00:00", "10:30:00", "789", "Eco", 5000.00));
        fares.add(createSampleFare("DEL-GAU-02", "Delhi", "Kolkata", "Gau", "12:00:00", "15:00:00", "654", "Exe", 8000.00));
        fares.add(createSampleFare("MUM-CHN-01", "Mumbai", null, "Chennai", "10:00:00", "12:30:00", "321", "Eco", 4500.00));
        fares.add(createSampleFare("DEL-MUM-01", "Delhi", null, "Mumbai", "14:00:00", "16:30:00", "987", "Exe", 7000.00));
        // Add more sample data as needed
        return fares;
    }

    // ... (other methods like getReservationForFlightAndDate, insertNewReservationRecord, etc., remain the same or updated as needed) ...
}