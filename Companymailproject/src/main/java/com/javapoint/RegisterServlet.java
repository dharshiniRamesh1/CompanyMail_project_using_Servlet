package com.javapoint;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email") + "@gmail.com";
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        String addressLine = request.getParameter("addressLine");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String country = request.getParameter("country");
        String contact = request.getParameter("contact");
        String registeredDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String authorized = "NO"; // Default value or based on some logic

        // Database connection
        String jdbcURL = "jdbc:mysql://localhost:3306/company_mail";
        String dbUser = "root";
        String dbPassword = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            String query = "INSERT INTO Users (NAME, EMAIL, PASSWORD, GENDER, DOB, ADDRESSLINE, CITY, STATE, COUNTRY, CONTACT, REGISTEREDDATE, AUTHORIZED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, gender);
            preparedStatement.setDate(5, java.sql.Date.valueOf(dob)); // Assuming dob is in yyyy-MM-dd format
            preparedStatement.setString(6, addressLine);
            preparedStatement.setString(7, city);
            preparedStatement.setString(8, state);
            preparedStatement.setString(9, country);
            preparedStatement.setString(10, contact);
            preparedStatement.setDate(11, java.sql.Date.valueOf(registeredDate)); // Assuming registeredDate is in yyyy-MM-dd format
            preparedStatement.setString(12, authorized);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
