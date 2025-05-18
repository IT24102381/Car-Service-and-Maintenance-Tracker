package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.controller;

import com.app.carmaintenance.car_maintenance.model.UserModel;
import com.app.carmaintenance.car_maintenance.util.UserUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("UserEmail");
        String password = request.getParameter("password");

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Email and password are required");
            request.getRequestDispatcher("UserModel.jsp").forward(request, response);
            return;
        }

        UserModel user = UserUtil.authenticate(email, password);
        if (user != null) {
            // Create session and store user details
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30 * 60);

            response.sendRedirect("DashboardServlet");
        } else {
            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("UserModel.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("UserModel.jsp").forward(request, response);
    }
}
