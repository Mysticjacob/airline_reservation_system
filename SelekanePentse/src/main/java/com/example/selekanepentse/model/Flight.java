package com.example.selekanepentse.model;

public class Flight {

    private String fName; // Flight Name
    private int fCode; // Flight Code
    private String cCode; // Class Code (e.g., "Eco", "Exe")
    private int tExeSeatNo; // Total Executive Seats
    private int tEcoSeatNo; // Total Economic Seats

    // Default constructor
    public Flight() {
    }

    // Constructor with all fields
    public Flight(String fName, int fCode, String cCode, int tExeSeatNo, int tEcoSeatNo) {
        this.fName = fName;
        this.fCode = fCode;
        this.cCode = cCode;
        this.tExeSeatNo = tExeSeatNo;
        this.tEcoSeatNo = tEcoSeatNo;
    }

    // Getters and Setters
    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public int getFCode() {
        return fCode;
    }

    public void setFCode(int fCode) {
        this.fCode = fCode;
    }

    public String getCCode() {
        return cCode;
    }

    public void setCCode(String cCode) {
        this.cCode = cCode;
    }

    public int getTExeSeatNo() {
        return tExeSeatNo;
    }

    public void setTExeSeatNo(int tExeSeatNo) {
        this.tExeSeatNo = tExeSeatNo;
    }

    public int getTEcoSeatNo() {
        return tEcoSeatNo;
    }

    public void setTEcoSeatNo(int tEcoSeatNo) {
        this.tEcoSeatNo = tEcoSeatNo;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "fName='" + fName + '\'' +
                ", fCode=" + fCode +
                ", cCode='" + cCode + '\'' +
                ", tExeSeatNo=" + tExeSeatNo +
                ", tEcoSeatNo=" + tEcoSeatNo +
                '}';
    }
}
