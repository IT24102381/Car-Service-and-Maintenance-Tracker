<%@ page import="com.app.carmaintenance.car_maintenance.model.AppointmentModel" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Car Maintenance Appointments</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Car Maintenance Appointments</h2>

    <a href="appointments?action=new" class="btn btn-primary mb-3">Add New Appointment</a>
    <a href="appointments?action=sort" class="btn btn-secondary mb-3">Sort by Date</a>
    <a href="DashboardServlet" class="btn btn-primary mb-3">Back to Dashboard</a>


    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Customer Name</th>
            <th>Vehicle Model</th>
            <th>Appointment Date</th>
            <th>Service Type</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<AppointmentModel> appointments =
                    (List<com.app.carmaintenance.car_maintenance.model.AppointmentModel>) request.getAttribute("appointments");

            if (appointments != null && !appointments.isEmpty()) {
                for (com.app.carmaintenance.car_maintenance.model.AppointmentModel appointment : appointments) {
        %>
        <tr>
            <td><%= appointment.getId() %></td>
            <td><%= appointment.getCustomerName() %></td>
            <td><%= appointment.getVehicleModel() %></td>
            <td><%= appointment.getAppointmentDate().toString() %></td>
            <td><%= appointment.getServiceType() %></td>
            <td><%= appointment.getStatus() %></td>
            <td>
                <a href="appointments?action=edit&id=<%= appointment.getId() %>" class="btn btn-sm btn-warning">Edit</a>
                <a href="appointments?action=delete&id=<%= appointment.getId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure?')">Delete</a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="7" class="text-center">No appointments found.</td>
        </tr>
        <%
            }
        %>

        </tbody>
    </table>
</div>
</body>
</html>
