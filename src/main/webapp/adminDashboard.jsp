<%@ page import="com.app.carmaintenance.car_maintenance.util.VehicleUtil" %>
<%@ page import="com.app.carmaintenance.car_maintenance.model.VehicleModel" %>
<%@ page import="com.app.carmaintenance.car_maintenance.model.ServiceRecord" %>
<%@ page import="com.app.carmaintenance.car_maintenance.util.UserUtil" %>
<%@ page import="com.app.carmaintenance.car_maintenance.model.UserModel" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Panel</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .service-table, .user-management {
            display: none;
        }
    </style>
</head>
<body class="bg-light">

<div class="container mt-4">

    <div class="d-flex justify-content-end mb-3">
        <a href="DashboardServlet?action=admin" class="btn btn-outline-primary me-2">Back to Dashboard</a>
        <a href="LogoutServlet" class="btn btn-outline-danger">Logout</a>
    </div>

    <h2 class="mb-4 text-center">Admin Dashboard</h2>

    <div class="card mb-4 shadow">
        <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
            <span>User Management</span>
            <button class="btn btn-sm btn-light" onclick="toggleSection('user-management')">Toggle User Management</button>
        </div>
        <div id="user-management" class="user-management card-body">
            <h5 class="card-title">All Users</h5>
            <a href="register.jsp?admin=true" class="btn btn-success mb-3">Create New Admin</a>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Email</th>
                    <th>Username</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% for (UserModel user : UserUtil.getAllUsers()) { %>
                <tr>
                    <td><%= user.getUserEmail() %></td>
                    <td><%= user.getUsername() %></td>
                    <td><%= user.getRole() %></td>
                    <td>
                        <form action="AdminDashboardServlet" method="post" onsubmit="return confirm('Delete this user account?')" class="d-inline">
                            <input type="hidden" name="action" value="deleteAccount">
                            <input type="hidden" name="targetUserEmail" value="<%= user.getUserEmail() %>">
                            <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                        </form>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <% for (VehicleUtil.Pair<VehicleModel, String> entry : VehicleUtil.getAllVehiclesWithOwners()) {
        VehicleModel vehicle = entry.getFirst();
        String ownerEmail = entry.getSecond();
        String vehicleId = vehicle.getVehicleNumber() + "_" + ownerEmail.replaceAll("[^a-zA-Z0-9]", "");
    %>

    <div class="card mb-4 shadow">
        <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
            <span>User: <%= ownerEmail %></span>
            <button class="btn btn-sm btn-light" onclick="toggleSection('service-<%= vehicleId %>')">Toggle Services</button>
        </div>
        <div class="card-body">
            <h5 class="card-title">Vehicle Info</h5>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Vehicle Number</th>
                    <th>Model</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><%= vehicle.getVehicleNumber() %></td>
                    <td><%= vehicle.getModel() %></td>
                    <td>
                        <form action="AdminDashboardServlet" method="post" onsubmit="return confirm('Delete this vehicle?')" class="d-inline">
                            <input type="hidden" name="action" value="deleteVehicle">
                            <input type="hidden" name="targetUserEmail" value="<%= ownerEmail %>">
                            <input type="hidden" name="vehicleNumber" value="<%= vehicle.getVehicleNumber() %>">
                            <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                        </form>
                    </td>
                </tr>
                </tbody>
            </table>

            <div id="service-<%= vehicleId %>" class="service-table mt-3">
                <h6>Service Records</h6>
                <table class="table table-striped table-bordered">
                    <thead class="table-secondary">
                    <tr>
                        <th>Date</th>
                        <th>Service Type</th>
                        <th>Mechanic</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (ServiceRecord sr : VehicleUtil.getServicesForVehicle(ownerEmail, vehicle.getVehicleNumber())) { %>
                    <tr>
                        <td><%= sr.getDate() %></td>
                        <td><%= sr.getServiceType() %></td>
                        <td><%= sr.getMechanic() %></td>
                        <td>
                            <form action="AdminDashboardServlet" method="post" onsubmit="return confirm('Delete this service record?')" class="d-inline">
                                <input type="hidden" name="action" value="deleteService">
                                <input type="hidden" name="targetUserEmail" value="<%= ownerEmail %>">
                                <input type="hidden" name="vehicleNumber" value="<%= vehicle.getVehicleNumber() %>">
                                <input type="hidden" name="serviceDate" value="<%= sr.getDate() %>">
                                <button type="submit" class="btn btn-outline-danger btn-sm">Delete</button>
                            </form>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <% } %>

</div>

<script>
    function toggleSection(id) {
        const el = document.getElementById(id);
        el.style.display = (el.style.display === "none" || el.style.display === "") ? "block" : "none";
    }
</script>

</body>
</html>