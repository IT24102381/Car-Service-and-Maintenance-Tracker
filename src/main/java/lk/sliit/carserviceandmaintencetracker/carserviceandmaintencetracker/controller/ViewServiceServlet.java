package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.controller;

import com.app.carmaintenance.car_maintenance.model.*;
import com.app.carmaintenance.car_maintenance.util.Config;
import com.app.carmaintenance.car_maintenance.util.FileUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.List;

@WebServlet("/ViewServiceServlet")
public class ViewServiceServlet extends HttpServlet {

    private static final String DATA_PATH = Config.CAR_DATA_DIR;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("UserModel.jsp");
            return;
        }

        String vehicleNumber = request.getParameter("vehicleNumber");
        if (vehicleNumber == null || vehicleNumber.isEmpty()) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        UserModel user = (UserModel) session.getAttribute("user");
        String userEmail = user.getUserEmail().replaceAll("[^a-zA-Z0-9]", "_");
        File userFile = new File(DATA_PATH + userEmail + ".txt");

        List<ServiceRecord> records = FileUtil.readServiceRecords(userFile, vehicleNumber);

        ServiceHistory history = new ServiceHistory();
        for (ServiceRecord record : records) {
            history.add(record);
        }

        history.selectionSortByDate();

        request.setAttribute("history", history.getAll());
        request.setAttribute("vehicleNumber", vehicleNumber);
        request.getRequestDispatcher("viewHistory.jsp").forward(request, response);
    }
}
