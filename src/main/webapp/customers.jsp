<%@ page import="com.app.carmaintenance.car_maintenance.model.CustomerModel" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Customer Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">Customer Management</h2>

    <%-- Success/Error Messages --%>
    <% if (request.getParameter("message") != null) { %>
    <div class="alert alert-success">${param.message}</div>
    <% } %>
    <% if (request.getParameter("error") != null) { %>
    <div class="alert alert-danger">${param.error}</div>
    <% } %>

    <div class="mb-3">
        <a href="add-customer.jsp" class="btn btn-primary">Add New Customer</a>
        <a href="DashboardServlet" class="btn btn-primary">Dashboard</a>
    </div>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<CustomerModel> customers = (List<CustomerModel>) request.getAttribute("customers");
            if (customers != null) {
                for (CustomerModel customer : customers) {
        %>
        <tr>
            <td><%= customer.getName() %></td>
            <td><%= customer.getEmail() %></td>
            <td><%= customer.getPhoneNumber() %></td>
            <td>
                <a href="customer?action=form&email=<%= customer.getEmail() %>" class="btn btn-sm btn-warning">Edit</a>
                <form action="customer" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="email" value="<%= customer.getEmail() %>">
                    <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure?')">Delete</button>
                </form>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>