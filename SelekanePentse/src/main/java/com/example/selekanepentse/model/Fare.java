package com.example.selekanepentse.model;


public class Fare {

    private String routeCode;
    private String sPlace; // Source Place
    private String via;
    private String dPlace; // Destination Place
    private String dTime; // Departure Time (consider using java.time.LocalTime)
    private String aTime; // Arrival Time (consider using java.time.LocalTime)
    private String fCode; // Flight Code
    private String cCode; // Class Code
    private double fare; // Fare amount (consider using BigDecimal)

    // Default constructor
    public Fare() {
    }

    // Constructor with all fields
    public Fare(String routeCode, String sPlace, String via, String dPlace, String dTime, String aTime, String fCode, String cCode, double fare) {
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

    @Override
    public String toString() {
        return "Fare{" +
                "routeCode='" + routeCode + '\'' +
                ", sPlace='" + sPlace + '\'' +
                ", via='" + via + '\'' +
                ", dPlace='" + dPlace + '\'' +
                ", dTime='" + dTime + '\'' +
                ", aTime='" + aTime + '\'' +
                ", fCode='" + fCode + '\'' +
                ", cCode='" + cCode + '\'' +
                ", fare=" + fare +
                '}';
    }
}