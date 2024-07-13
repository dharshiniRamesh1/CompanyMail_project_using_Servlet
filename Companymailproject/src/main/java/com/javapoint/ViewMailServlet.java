package com.javapoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ViewMailServlet")
public class ViewMailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("header.html").include(request, response);
        request.getRequestDispatcher("link.html").include(request, response);

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("index.html");
        } else {
            String email = (String) session.getAttribute("email");
            out.print("<span style='float:right'>Hi, " + email + "</span>");
            out.print("<h1>View Mail</h1>");

            int id = Integer.parseInt(request.getParameter("id"));
            try {
                Connection con = ConProvider.getConnection();
                PreparedStatement ps = con.prepareStatement("select * from company_mailer_message where id=?");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    out.print("<table border='1' style='width:700px;'>");
                    out.print("<tr><td>From:</td><td>" + rs.getString("sender") + "</td></tr>");
                    out.print("<tr><td>To:</td><td>" + rs.getString("receiver") + "</td></tr>");
                    out.print("<tr><td>Subject:</td><td>" + rs.getString("subject") + "</td></tr>");
                    out.print("<tr><td>Message:</td><td>" + rs.getString("message") + "</td></tr>");
                    out.print("<tr><td>Date:</td><td>" + rs.getString("messagedate") + "</td></tr>");
                    out.print("</table>");
                }
                con.close();
            } catch (Exception e) {
                out.print(e);
            }
        }

        request.getRequestDispatcher("footer.html").include(request, response);
        out.close();
    }
}
