package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.controller;

import com.app.carmaintenance.car_maintenance.model.UserModel;
import com.app.carmaintenance.car_maintenance.model.VehicleModel;
import com.app.carmaintenance.car_maintenance.util.Config;
import com.app.carmaintenance.car_maintenance.util.FileUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.List;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final String BASE_PATH = Config.CAR_DATA_DIR;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        String action = request.getParameter("action");

        if (user == null && "admin".equalsIgnoreCase(action)) {
            user = new UserModel("admin@admin.com", "admin", "Admin", "0000000000");
            user.setRole("ADMIN");
            session.setAttribute("user", user);
        }

        if (user == null) {
            response.sendRedirect("UserModel.jsp");
            return;
        }

        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
            return;
        }

        File dir = new File(BASE_PATH);
        if (!dir.exists()) dir.mkdirs();

        String sanitizedEmail = user.getUserEmail().replaceAll("[^a-zA-Z0-9]", "_");
        File userFile = new File(BASE_PATH + sanitizedEmail + ".txt");

        List<VehicleModel> vehicles = FileUtil.readVehiclesFromFile(userFile);

        request.setAttribute("vehicles", vehicles);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
