// File: src/main/java/com/example/motebangtatolo/service/CustomerService.java

package com.example.motebangtatolo.service;

import com.example.motebangtatolo.model.Customer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerService {

    public void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer_details (t_date, cust_name, father_name, gender, d_o_b, address, tel_no, profession, security, concession) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DatabaseMetaData DatabaseConnection = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getTDate()); // Validate date format (e.g., LocalDate.parse)
            stmt.setString(2, customer.getCustName());
            stmt.setString(3, customer.getFatherName());
            stmt.setString(4, customer.getGender());
            stmt.setString(5, customer.getDob()); // Validate date format (e.g., LocalDate.parse)
            stmt.setString(6, customer.getAddress());
            stmt.setLong(7, customer.getTelNo());
            stmt.setString(8, customer.getProfession());
            stmt.setString(9, customer.getSecurity());
            stmt.setString(10, customer.getConcession());

            stmt.executeUpdate();
            System.out.println("Customer added to database: " + customer.getCustName());
        }
    }

    // Add other methods like getCustomerByXXX, updateCustomer, deleteCustomer if needed later
}