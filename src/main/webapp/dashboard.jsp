<%@ page import="com.app.carmaintenance.car_maintenance.model.UserModel" %>
<%@ page import="com.app.carmaintenance.car_maintenance.model.VehicleModel" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    UserModel user = (UserModel) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("UserModel.jsp");
        return;
    }

    List<VehicleModel> vehicles = (List<VehicleModel>) request.getAttribute("vehicles");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
</head>
<style>
    .btn-group .btn {
        transition: 0.2s ease-in-out;
    }
    .btn-danger:hover {
        background-color: #c82333;
    }
    .btn-success:hover {
        background-color: #218838;
    }
    .btn-primary:hover {
        background-color: #0069d9;
    }
    .btn-group form {
        margin: 0;
    }
    .btn-group form .btn {
        border-radius: 0;
    }
    .action-buttons {
        display: flex;
        gap: 10px;
        flex-wrap: wrap;
        margin-bottom: 20px;
    }
</style>
<body>
<div class="container mt-5">
    <h2>Welcome, <%= user.getUsername().toUpperCase() %>!</h2>
    <hr>

    <div class="action-buttons">
        <div class="btn-group" role="group" aria-label="Vehicle Actions">
            <a href="newcaradding.jsp" class="btn btn-success me-2">
                <i class="bi bi-car-front"></i> Add Vehicle
            </a>
            <a href="appointments" class="btn btn-primary me-2">
                <i class="bi bi-calendar-plus"></i> Make Appointment
            </a>
        </div>

        <div class="btn-group" role="group" aria-label="Customer Actions">
            <a href="customer?action=list" class="btn btn-info me-2">
                <i class="bi bi-person-plus"></i> Add/View Customer Details
            </a>
        </div>

        <div class="btn-group" role="group" aria-label="Account Actions">
            <a href="LogoutServlet" class="btn btn-secondary">
                <i class="bi bi-box-arrow-right"></i> Logout
            </a>
            <form action="DeleteAccountServlet" method="post"
                  onsubmit="return confirm('Are you sure you want to delete your account? This action cannot be undone.');">
                <button type="submit" class="btn btn-danger">
                    <i class="bi bi-trash"></i> Delete Account
                </button>
            </form>
        </div>
    </div>

    <h4><i class="bi bi-car-front"></i> Your Vehicles</h4>
    <%
        if (vehicles == null || vehicles.isEmpty()) {
    %>
    <div class="alert alert-info">
        You haven't added any vehicles yet.
    </div>
    <%
    } else {
    %>
    <table class="table table-striped">
        <thead class="table-dark">
        <tr>
            <th>Vehicle Number</th>
            <th>Model</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (VehicleModel vehicle : vehicles) { %>
        <tr>
            <td><%= vehicle.getVehicleNumber() %></td>
            <td><%= vehicle.getModel() %></td>
            <td>
                <a href="ViewServiceServlet?vehicleNumber=<%= vehicle.getVehicleNumber() %>"
                   class="btn btn-info btn-sm">
                    <i class="bi bi-clock-history"></i> View History
                </a>
                <a href="addServices.jsp?vehicleNumber=<%= vehicle.getVehicleNumber() %>"
                   class="btn btn-warning btn-sm">
                    <i class="bi bi-wrench"></i> Add Service
                </a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <%
        }
    %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>