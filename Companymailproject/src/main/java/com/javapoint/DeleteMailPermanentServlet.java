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

@WebServlet("/DeleteMailPermanentServlet")
public class DeleteMailPermanentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Connection con = ConProvider.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from company_mailer_message where id=?");
            ps.setInt(1, id);
            int status = ps.executeUpdate();
            if (status > 0) {
                response.sendRedirect("TrashServlet");
            } else {
                out.print("Unable to delete mail permanently");
            }
            con.close();
        } catch (Exception e) {
            out.print(e);
        }
        out.close();
    }
}

