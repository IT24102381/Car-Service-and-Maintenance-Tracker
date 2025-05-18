package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.controller;

import lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.model.AdminModel;
import lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.model.UserModel;
import lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.util.UserUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("UserEmail");
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");

        // Get current user from session
        HttpSession session = request.getSession(false);
        UserModel currentUser = (session != null) ? (UserModel) session.getAttribute("user") : null;

        UserModel newUser;

        // Only allow ADMIN role to be set if current user is admin AND they selected ADMIN role
        if (currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole())
                && "ADMIN".equalsIgnoreCase(role)) {
            newUser = new AdminModel(email, password, username, phone);
        } else {
            newUser = new UserModel(email, password, username, phone, "USER");
        }

        if (UserUtil.registerUser(newUser)) {
            if (currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole())) {
                response.sendRedirect("adminDashboard.jsp?success=User created successfully");
            } else {
                response.sendRedirect("UserModel.jsp?success=Registration successful");
            }
        } else {
            request.setAttribute("error", "User already exists!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}