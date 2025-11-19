package com.example.mothaelehloara.model;

public class Fleet {

    private String noAircraft; // Number of Aircraft
    private String clubPreCapacity;
    private String ecoCapacity;
    private String engineType;
    private String cruiseSpeed;
    private String airLength;
    private String wingSpan; // Wing Span (corrected spelling)

    // Default constructor
    public Fleet() {
    }

    // Constructor with all fields
    public Fleet(String noAircraft, String clubPreCapacity, String ecoCapacity, String engineType, String cruiseSpeed, String airLength, String wingSpan) {
        this.noAircraft = noAircraft;
        this.clubPreCapacity = clubPreCapacity;
        this.ecoCapacity = ecoCapacity;
        this.engineType = engineType;
        this.cruiseSpeed = cruiseSpeed;
        this.airLength = airLength;
        this.wingSpan = wingSpan;
    }

    // Getters and Setters
    public String getNoAircraft() {
        return noAircraft;
    }

    public void setNoAircraft(String noAircraft) {
        this.noAircraft = noAircraft;
    }

    public String getClubPreCapacity() {
        return clubPreCapacity;
    }

    public void setClubPreCapacity(String clubPreCapacity) {
        this.clubPreCapacity = clubPreCapacity;
    }

    public String getEcoCapacity() {
        return ecoCapacity;
    }

    public void setEcoCapacity(String ecoCapacity) {
        this.ecoCapacity = ecoCapacity;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getCruiseSpeed() {
        return cruiseSpeed;
    }

    public void setCruiseSpeed(String cruiseSpeed) {
        this.cruiseSpeed = cruiseSpeed;
    }

    public String getAirLength() {
        return airLength;
    }

    public void setAirLength(String airLength) {
        this.airLength = airLength;
    }

    public String getWingSpan() {
        return wingSpan;
    }

    public void setWingSpan(String wingSpan) {
        this.wingSpan = wingSpan;
    }

    @Override
    public String toString() {
        return "Fleet{" +
                "noAircraft='" + noAircraft + '\'' +
                ", clubPreCapacity='" + clubPreCapacity + '\'' +
                ", ecoCapacity='" + ecoCapacity + '\'' +
                ", engineType='" + engineType + '\'' +
                ", cruiseSpeed='" + cruiseSpeed + '\'' +
                ", airLength='" + airLength + '\'' +
                ", wingSpan='" + wingSpan + '\'' +
                '}';
    }
}
