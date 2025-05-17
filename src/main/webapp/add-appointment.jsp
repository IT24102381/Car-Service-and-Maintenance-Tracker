<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${empty appointment ? 'Add' : 'Edit'} Appointment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>${empty appointment ? 'Add New' : 'Edit'} Appointment</h2>

    <form action="appointments" method="post">
        <input type="hidden" name="action" value="${empty appointment ? 'add' : 'update'}">
        <c:if test="${not empty appointment}">
            <input type="hidden" name="id" value="${appointment.id}">
        </c:if>

        <div class="mb-3">
            <label for="customerName" class="form-label">Customer Name:</label>
            <input type="text" class="form-control" id="customerName" name="customerName"
                   value="${appointment.customerName}" required>
        </div>

        <div class="mb-3">
            <label for="vehicleModel" class="form-label">Vehicle Model:</label>
            <input type="text" class="form-control" id="vehicleModel" name="vehicleModel"
                   value="${appointment.vehicleModel}" required>
        </div>

        <div class="mb-3">
            <label for="appointmentDate" class="form-label">Appointment Date & Time:</label>
            <input type="datetime-local" class="form-control" id="appointmentDate" name="appointmentDate"
                   value="${appointment.appointmentDate}" required>
        </div>

        <div class="mb-3">
            <label for="serviceType" class="form-label">Service Type:</label>
            <select class="form-select" id="serviceType" name="serviceType" required>
                <option value="">Select Service</option>
                <option value="Oil Change" ${appointment.serviceType == 'Oil Change' ? 'selected' : ''}>Oil Change</option>
                <option value="Tire Rotation" ${appointment.serviceType == 'Tire Rotation' ? 'selected' : ''}>Tire Rotation</option>
                <option value="Brake Inspection" ${appointment.serviceType == 'Brake Inspection' ? 'selected' : ''}>Brake Inspection</option>
                <option value="Engine Diagnostic" ${appointment.serviceType == 'Engine Diagnostic' ? 'selected' : ''}>Engine Diagnostic</option>
                <option value="Full Service" ${appointment.serviceType == 'Full Service' ? 'selected' : ''}>Full Service</option>
            </select>
        </div>

        <c:if test="${not empty appointment}">
            <div class="mb-3">
                <label for="status" class="form-label">Status:</label>
                <select class="form-select" id="status" name="status">
                    <option value="Scheduled" ${appointment.status == 'Scheduled' ? 'selected' : ''}>Scheduled</option>
                    <option value="In Progress" ${appointment.status == 'In Progress' ? 'selected' : ''}>In Progress</option>
                    <option value="Completed" ${appointment.status == 'Completed' ? 'selected' : ''}>Completed</option>
                    <option value="Cancelled" ${appointment.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                </select>
            </div>
        </c:if>

        <button type="submit" class="btn btn-primary">Save</button>
        <a href="appointments" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>