// File: src/main/java/com/example/motebangtatolo/model/ReservedSeat.java

package com.example.motebangtatolo.model;

/**
 * Model class representing the RESULT of a ticket booking attempt.
 * This includes the generated PNR, assigned seat number (if confirmed),
 * and waiting list number (if applicable).
 * This is distinct from the RESERVATION_SEAT table which tracks counts of reserved seats per flight/date.
 */
public class ReservedSeat {

    private String fCode; // Flight Code (associated with the booking)
    private String tDate; // Travel Date (associated with the booking)
    private int waitingNo; // Waiting List Number (0 if Confirmed)
    private String pnrNumber; // Generated PNR Number
    private String seatNumber; // Assigned Seat Number (e.g., "E001", "X015") - Can be null if on waiting list

    // Default constructor
    public ReservedSeat() {
    }

    // Constructor for confirmed booking (seat number assigned)
    public ReservedSeat(String fCode, String tDate, int waitingNo, String pnrNumber, String seatNumber) {
        this.fCode = fCode;
        this.tDate = tDate;
        this.waitingNo = waitingNo; // Should be 0 for confirmed
        this.pnrNumber = pnrNumber;
        this.seatNumber = seatNumber; // e.g., "E001", "X015"
    }

    // Constructor for waiting list entry (no seat number assigned yet)
    public ReservedSeat(String fCode, String tDate, int waitingNo, String pnrNumber) {
        this(fCode, tDate, waitingNo, pnrNumber, null); // Seat number is null for waiting list
    }

    // Getters and Setters
    public String getFCode() {
        return fCode;
    }

    public void setFCode(String fCode) {
        this.fCode = fCode;
    }

    public String getTDate() {
        return tDate;
    }

    public void setTDate(String tDate) {
        this.tDate = tDate;
    }

    public int getWaitingNo() {
        return waitingNo;
    }

    public void setWaitingNo(int waitingNo) {
        this.waitingNo = waitingNo;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return "ReservedSeat{" +
                "fCode='" + fCode + '\'' +
                ", tDate='" + tDate + '\'' +
                ", waitingNo=" + waitingNo +
                ", pnrNumber='" + pnrNumber + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                '}';
    }

    public void setTResEcoSeat(int i) {
    }

    public void setTResExeSeat(int i) {
    }

    public int getTResEcoSeat() {

        return 0;
    }

    public int getTResExeSeat() {
        return 0;
    }
}