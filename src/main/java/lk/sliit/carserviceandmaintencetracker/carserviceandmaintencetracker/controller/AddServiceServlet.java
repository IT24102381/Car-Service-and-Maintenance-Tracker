package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.controller;

import com.app.carmaintenance.car_maintenance.model.ServiceRecord;
import com.app.carmaintenance.car_maintenance.model.UserModel;
import com.app.carmaintenance.car_maintenance.util.FileUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/AddServiceServlet")
public class AddServiceServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("UserModel.jsp");
            return;
        }

        String vehicleNumber = request.getParameter("vehicleNumber");
        String date = request.getParameter("date");
        String serviceType = request.getParameter("serviceType");
        String mechanic = request.getParameter("mechanic");
        String oilRenewDate = request.getParameter("oilRenewDate");
        String lastServiceDate = request.getParameter("lastServiceDate");
        String alignmentCheckDate = request.getParameter("alignmentCheckDate");
        String tyreRenewDate = request.getParameter("tyreRenewDate");
        String specialNotice = request.getParameter("specialNotice");

        ServiceRecord newService = new ServiceRecord(date, serviceType, mechanic,
                oilRenewDate, lastServiceDate, alignmentCheckDate, tyreRenewDate, specialNotice);

        String userEmail = ((UserModel) session.getAttribute("user")).getUserEmail();

        FileUtil.appendServiceRecord(userEmail, vehicleNumber, newService);

        response.sendRedirect("DashboardServlet");
    }
}
