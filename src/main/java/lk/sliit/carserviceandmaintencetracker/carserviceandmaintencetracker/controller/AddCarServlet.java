package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.controller;

import lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.model.UserModel;
import lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.model.VehicleModel;
import lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.util.FileUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/AddCarServlet")
public class AddCarServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("UserModel.jsp");
            return;
        }

        String vehicleName = request.getParameter("vehicleName");
        String numberPlate = request.getParameter("numberPlate");

        VehicleModel vehicle = new VehicleModel(numberPlate, vehicleName);

        FileUtil.appendVehicle(user.getUserEmail(), vehicle);

        response.sendRedirect("DashboardServlet");
    }
}
