package com.javapoint;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ComposeServletProcess")
public class ComposeServletProcess extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("header.html").include(request, response);

        String receiver = request.getParameter("to");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
        message = message.replaceAll("\n", "<br/>");
        HttpSession session = request.getSession(false);
        
        if (session == null) {
            response.sendRedirect("index.html");
            return;
        }

        String email = (String) session.getAttribute("email");
        
        if (email == null) {
            response.sendRedirect("login.html");
            return;
        }

        int status = ComposeDao.save(email, receiver, subject, message);
        if (status > 0) {
            request.setAttribute("msg", "Message successfully sent");
            request.getRequestDispatcher("ComposeServlet").forward(request, response);
        } else {
            out.print("<p>Message sending failed</p>");
            request.getRequestDispatcher("composeform.html").include(request, response);
        }

        request.getRequestDispatcher("footer.html").include(request, response);
        out.close();
    }
}
