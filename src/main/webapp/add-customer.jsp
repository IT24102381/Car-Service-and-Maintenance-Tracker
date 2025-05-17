<%@ page import="com.app.carmaintenance.car_maintenance.model.CustomerModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title><%= request.getParameter("email") == null ? "Add" : "Edit" %> Customer</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4"><%= request.getParameter("email") == null ? "Add New" : "Edit" %> Customer</h2>

    <form action="customer" method="post">
        <input type="hidden" name="action" value="save">

        <%
            CustomerModel customer = (CustomerModel) request.getAttribute("customer");
            String email = customer != null ? customer.getEmail() : "";
            String name = customer != null ? customer.getName() : "";
            String phone = customer != null ? customer.getPhoneNumber() : "";
        %>

        <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <input type="text" class="form-control" id="name" name="name"
                   value="<%= name %>" required>
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email"
                   value="<%= email %>" <%= email.isEmpty() ? "" : "readonly" %> required>
        </div>

        <div class="mb-3">
            <label for="phone" class="form-label">Phone Number</label>
            <input type="tel" class="form-control" id="phone" name="phone"
                   value="<%= phone %>" required>
        </div>

        <button type="submit" class="btn btn-primary">Save</button>
        <a href="customer?action=list" class="btn btn-secondary">Cancel</a>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>