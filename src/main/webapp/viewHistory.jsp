<%@ page import="com.app.carmaintenance.car_maintenance.model.ServiceRecord" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Service History</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3>Service History for Vehicle: <%= request.getAttribute("vehicleNumber") %></h3>
        <a href="DashboardServlet" class="btn btn-secondary">Back to Dashboard</a>
    </div>

    <%
        List<ServiceRecord> history = (List<ServiceRecord>) request.getAttribute("history");
        if (history == null || history.isEmpty()) {
    %>
    <div class="alert alert-warning text-center">No service records found.</div>
    <%
    } else {
    %>
    <table class="table table-bordered table-hover table-striped">
        <thead class="table-dark text-center">
        <tr>
            <th>Date</th>
            <th>Service Type</th>
            <th>Mechanic</th>
            <th>Oil Renew Date</th>
            <th>Last Service Date</th>
            <th>Alignment Check Date</th>
            <th>Tyre Renew Date</th>
            <th>Special Notice</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (ServiceRecord record : history) {
        %>
        <tr>
            <td><%= record.getDate() %></td>
            <td><%= record.getServiceType() %></td>
            <td><%= record.getMechanic() %></td>
            <td><%= record.getOilRenewDate() %></td>
            <td><%= record.getLastServiceDate() %></td>
            <td><%= record.getAlignmentCheckDate() %></td>
            <td><%= record.getTyreRenewDate() %></td>
            <td><%= record.getSpecialNotice() %></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
        }
    %>
</div>

</body>
</html>

