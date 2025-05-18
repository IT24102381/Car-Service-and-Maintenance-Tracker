package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.controller;

import lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.model.UserModel;
import lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.util.FileUtil;
import lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.util.UserUtil;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserModel user = (UserModel) session.getAttribute("user");

        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized access");
            return;
        }

        String action = request.getParameter("action");
        String email = request.getParameter("targetUserEmail");
        String vehicleNumber = request.getParameter("vehicleNumber");

        try {
            switch (action) {
                case "deleteVehicle":
                    FileUtil.deleteVehicleFromUserFile(email, vehicleNumber);
                    break;
                case "deleteService":
                    String serviceDate = request.getParameter("serviceDate");
                    FileUtil.deleteServiceFromUserFile(email, vehicleNumber, serviceDate);
                    break;
                case "deleteAccount":
                    UserUtil.deleteUserAccount(email);
                    break;
                case "deleteCustomer":
                    String customerEmail = request.getParameter("targetUserEmail");
                    if (UserUtil.deleteCustomer(customerEmail)) {
                        response.sendRedirect("adminDashboard.jsp?success=Customer+deleted+successfully");
                    } else {
                        response.sendRedirect("adminDashboard.jsp?error=Failed+to+delete+customer");
                    }
                    break;
            }

            response.sendRedirect("adminDashboard.jsp?success=true");

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
        }
    }
}