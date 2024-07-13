package com.javapoint;

import java.sql.*;

public class RegisterDao {
	public static int save(String name, String email, String password, String gender, String dob, String addressLine, String city, String state, String country, String contact) {
	    int status = 0;
	    java.sql.Date sqlDOB = Formatter.getSqlDate(dob); // Assuming this method is defined elsewhere

	    try (Connection con = ConProvider.getConnection();
	         PreparedStatement ps = con.prepareStatement("INSERT INTO company_mailer_user (name, email, password, gender, dob, addressLine, city, state, country, contact, registereddate, authorized) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

	        // Set parameters for the prepared statement
	        ps.setString(1, name);
	        ps.setString(2, email);
	        ps.setString(3, password);
	        ps.setString(4, gender);
	        ps.setDate(5, sqlDOB);
	        ps.setString(6, addressLine);
	        ps.setString(7, city);
	        ps.setString(8, state);
	        ps.setString(9, country);
	        ps.setString(10, contact);
	        ps.setDate(11, Formatter.getCurrentDate());
	        ps.setString(12, "yes");

	        // Execute the update and get the status
	        status = ps.executeUpdate();

	        if (status > 0) {
	            System.out.println("Registration successful for user: " + name);
	        } else {
	            System.out.println("Registration failed for user: " + name);
	        }

	    } catch (SQLException e) {
	        System.out.println("SQL Exception: " + e.getMessage());
	        e.printStackTrace(); // Print detailed stack trace for debugging
	    } catch (Exception e) {
	        System.out.println("Exception: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return status;
	}

    // Return the status of the database operation
    
}

