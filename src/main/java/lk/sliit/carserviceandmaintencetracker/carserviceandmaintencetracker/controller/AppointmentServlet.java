package com.app.carmaintenance.car_maintenance.controller;

import com.app.carmaintenance.car_maintenance.model.AppointmentModel;
import com.app.carmaintenance.car_maintenance.util.AppointmentUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet("/appointments")
public class AppointmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("UserModel.jsp");
            return;
        }

        String action = request.getParameter("action");
        List<AppointmentModel> appointments = new ArrayList<>();
        try {
            appointments = AppointmentUtil.readAppointments();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if ("new".equals(action)) {
            request.setAttribute("appointment", null);
            request.getRequestDispatcher("add-appointment.jsp").forward(request, response);
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            AppointmentModel appointment = AppointmentUtil.getById(appointments, id);
            request.setAttribute("appointment", appointment);
            request.getRequestDispatcher("add-appointment.jsp").forward(request, response);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            appointments.removeIf(a -> a.getId() == id);
            AppointmentUtil.saveAppointments(appointments);
            response.sendRedirect("appointments");
        } else if ("sort".equals(action)) {
            appointments.sort(Comparator.comparing(AppointmentModel::getAppointmentDate));
            request.setAttribute("appointments", appointments);
            request.getRequestDispatcher("appointment-list.jsp").forward(request, response);
        } else {
            request.setAttribute("appointments", appointments);
            request.getRequestDispatcher("appointment-list.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("UserModel.jsp");
            return;
        }

        List<AppointmentModel> appointments = new ArrayList<>();
        try {
            appointments = AppointmentUtil.readAppointments();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String action = request.getParameter("action");
        String customerName = request.getParameter("customerName");
        String vehicleModel = request.getParameter("vehicleModel");
        LocalDateTime appointmentDate = LocalDateTime.parse(request.getParameter("appointmentDate"));
        String serviceType = request.getParameter("serviceType");

        if ("add".equals(action)) {
            int id = AppointmentUtil.generateNewId(appointments);
            AppointmentModel newAppointment = new AppointmentModel(id, customerName, vehicleModel,
                    appointmentDate, serviceType, "Scheduled");
            appointments.add(newAppointment);
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            AppointmentModel appointment = AppointmentUtil.getById(appointments, id);
            if (appointment != null) {
                appointment.setCustomerName(customerName);
                appointment.setVehicleModel(vehicleModel);
                appointment.setAppointmentDate(appointmentDate);
                appointment.setServiceType(serviceType);
                appointment.setStatus(request.getParameter("status"));
            }
        }

        try {
            AppointmentUtil.saveAppointments(appointments);
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.sendRedirect("appointments");
    }

}
