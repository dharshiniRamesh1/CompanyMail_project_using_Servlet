package com.javapoint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class ComposeDao {

    public static int save(String sender, String receiver, String subject, String message) {
        int status = 0;
        try (Connection con = ConProvider.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO company_mailer_message (sender, receiver, subject, message, trash, messagedate) VALUES (?, ?, ?, ?, ?, ?)")) {
            
            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.setString(3, subject);
            ps.setString(4, message);
            ps.setString(5, "no");
            ps.setDate(6, new Date(System.currentTimeMillis()));
            
            status = ps.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return status;
    }
}

