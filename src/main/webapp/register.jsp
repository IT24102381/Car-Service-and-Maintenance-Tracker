<%@ page import="com.app.carmaintenance.car_maintenance.model.UserModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="login-container">
        <h2>Register</h2>

        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="RegisterServlet" method="post">
            <div class="mb-3">
                <label for="UserEmail" class="form-label">Email address</label>
                <input type="email" class="form-control" id="UserEmail" name="UserEmail" required>
            </div>

            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" id="username" name="username" required>
            </div>

            <div class="mb-3">
                <label for="phone" class="form-label">Phone Number</label>
                <input type="text" class="form-control" id="phone" name="phone" required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>

            <%
                HttpSession session1 = request.getSession(false);
                UserModel currentUser = (session1 != null) ? (UserModel) session1.getAttribute("user") : null;
                if (currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            %>
            <div class="mb-3">
                <label for="role" class="form-label">User Role</label>
                <select class="form-control" id="role" name="role" required>
                    <option value="USER">Regular User</option>
                    <option value="ADMIN">Administrator</option>
                </select>
            </div>
            <% } else { %>
            <input type="hidden" name="role" value="USER">
            <% } %>

            <button type="submit" class="btn btn-primary">Register</button>
            <a href="UserModel.jsp" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>