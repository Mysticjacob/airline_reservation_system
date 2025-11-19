// File: src/main/java/com/example/leratothite/model/Timing.java

package com.example.leratothite.model;

/**
 * Model class representing flight timing information, primarily sourced from the FARE table.
 * This class holds the essential details displayed in the Show Timings view.
 */
public class Timing {

    // Fields from the FARE table (as per name.PDF)
    private String routeCode;
    private String sPlace; // Source Place
    private String via;
    private String dPlace; // Destination Place
    private String dTime; // Departure Time (consider using java.time.LocalTime)
    private String aTime; // Arrival Time (consider using java.time.LocalTime)
    private String fCode; // Flight Code
    private String cCode; // Class Code (e.g., "Eco", "Exe")
    private double fare; // Fare amount (consider using java.math.BigDecimal)

    // Default constructor
    public Timing() {
    }

    // Constructor with all fields
    public Timing(String routeCode, String sPlace, String via, String dPlace, String dTime, String aTime, String fCode, String cCode, double fare) {
        this.routeCode = routeCode;
        this.sPlace = sPlace;
        this.via = via;
        this.dPlace = dPlace;
        this.dTime = dTime;
        this.aTime = aTime;
        this.fCode = fCode;
        this.cCode = cCode;
        this.fare = fare;
    }

    // Getters and Setters
    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getSPlace() {
        return sPlace;
    }

    public void setSPlace(String sPlace) {
        this.sPlace = sPlace;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getDPlace() {
        return dPlace;
    }

    public void setDPlace(String dPlace) {
        this.dPlace = dPlace;
    }

    public String getDTime() {
        return dTime;
    }

    public void setDTime(String dTime) {
        this.dTime = dTime;
    }

    public String getATime() {
        return aTime;
    }

    public void setATime(String aTime) {
        this.aTime = aTime;
    }

    public String getFCode() {
        return fCode;
    }

    public void setFCode(String fCode) {
        this.fCode = fCode;
    }

    public String getCCode() {
        return cCode;
    }

    public void setCCode(String cCode) {
        this.cCode = cCode;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    // --- Direct Getters for TableView PropertyValueFactory (CRITICAL) ---
    // These are often required by PropertyValueFactory if the standard getter name doesn't match the property name exactly.
    // JavaFX often looks for get<FieldName> or is<FieldName> (for booleans).
    public String getfCode() { // Note: Lowercase 'f' - often used by PropertyValueFactory
        return getFCode(); // Delegate to the standard getter
    }

    public String getfName() { // Placeholder: The FARE table doesn't have F_NAME directly.
        // In a real scenario, you might fetch the flight name from FLIGHT_INFORMATION based on fCode and store it here.
        // For now, returning the F_CODE as a stand-in or a derived name.
        return getFCode(); // Or get a proper name if available from joined data
    }

    public String getdTime() {
        return getDTime();
    }

    public String getaTime() {
        return getATime();
    }

    public String getcCode() {
        return getCCode();
    }

    public double getfare() { // Renamed to avoid conflict with the 'fare' field name if PropertyValueFactory used 'fare' directly
        return getFare();
    }

    @Override
    public String toString() {
        return "Timing{" +
                "routeCode='" + routeCode + '\'' +
                ", sPlace='" + sPlace + '\'' +
                ", dPlace='" + dPlace + '\'' +
                ", dTime='" + dTime + '\'' +
                ", aTime='" + aTime + '\'' +
                ", fCode='" + fCode + '\'' +
                ", cCode='" + cCode + '\'' +
                ", fare=" + fare +
                '}';
    }
}