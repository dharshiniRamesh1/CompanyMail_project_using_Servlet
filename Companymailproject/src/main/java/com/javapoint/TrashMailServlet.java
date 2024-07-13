package com.javapoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TrashMailServlet")
public class TrashMailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Connection con = ConProvider.getConnection();
            PreparedStatement ps = con.prepareStatement("update company_mailer_message set trash=? where id=?");
            ps.setString(1, "yes");
            ps.setInt(2, id);
            int status = ps.executeUpdate();
            if (status > 0) {
                response.sendRedirect("InboxServlet");
            } else {
                out.print("Unable to move mail to trash");
            }
            con.close();
        } catch (Exception e) {
            out.print(e);
        }
        out.close();
    }
}

