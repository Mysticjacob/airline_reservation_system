// File: src/main/java/com/example/motebangtatolo/model/Customer.java

package com.example.motebangtatolo.model;

/**
 * Model class representing the CUSTOMER DETAILS table.
 */
public class Customer {

    private String tDate; // Travel Date
    private String custName;
    private String fatherName;
    private String gender;
    private String dob; // Date of Birth (consider using java.time.LocalDate)
    private String address;
    private long telNo; // Telephone Number
    private String profession;
    private String security; // Security details
    private String concession; // Concession type

    // Default constructor
    public Customer() {
    }

    // Constructor with all fields
    public Customer(String tDate, String custName, String fatherName, String gender, String dob, String address, long telNo, String profession, String security, String concession) {
        this.tDate = tDate;
        this.custName = custName;
        this.fatherName = fatherName;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.telNo = telNo;
        this.profession = profession;
        this.security = security;
        this.concession = concession;
    }

    // Getters and Setters
    public String getTDate() {
        return tDate;
    }

    public void setTDate(String tDate) {
        this.tDate = tDate;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getTelNo() {
        return telNo;
    }

    public void setTelNo(long telNo) {
        this.telNo = telNo;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getConcession() {
        return concession;
    }

    public void setConcession(String concession) {
        this.concession = concession;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "tDate='" + tDate + '\'' +
                ", custName='" + custName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                ", telNo=" + telNo +
                ", profession='" + profession + '\'' +
                ", security='" + security + '\'' +
                ", concession='" + concession + '\'' +
                '}';
    }
}