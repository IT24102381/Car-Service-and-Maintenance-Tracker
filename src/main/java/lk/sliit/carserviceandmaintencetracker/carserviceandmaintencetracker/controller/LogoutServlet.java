package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rs)
            throws ServletException, IOException {

        HttpSession session = rq.getSession(false); // false = don't create a new one if it doesn't exist
        if (session != null) {
            session.invalidate();
        }
        rs.sendRedirect("UserModel.jsp");
    }
}
