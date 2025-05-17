<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%
  String vehicleNumber = request.getParameter("vehicleNumber");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Vehicle Service Tracker</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h3 class="mb-4">Add Service for <%= vehicleNumber %></h3>
  <form action="AddServiceServlet" method="post">
    <input type="hidden" name="vehicleNumber" value="<%= vehicleNumber %>"/>

    <div class="mb-3">
      <label class="form-label">Service Date</label>
      <input type="date" name="date" class="form-control" required/>
    </div>

    <div class="mb-3">
      <label class="form-label">Service Type</label>
      <input type="text" name="serviceType" class="form-control" required/>
    </div>

    <div class="mb-3">
      <label class="form-label">Mechanic</label>
      <input type="text" name="mechanic" class="form-control" required/>
    </div>

    <div class="mb-3">
      <label class="form-label">Oil Renew Date</label>
      <input type="date" name="oilRenewDate" class="form-control"/>
    </div>

    <div class="mb-3">
      <label class="form-label">Last Service Date</label>
      <input type="date" name="lastServiceDate" class="form-control"/>
    </div>

    <div class="mb-3">
      <label class="form-label">Alignment Check Date</label>
      <input type="date" name="alignmentCheckDate" class="form-control"/>
    </div>

    <div class="mb-3">
      <label class="form-label">Tyre Renew Date</label>
      <input type="date" name="tyreRenewDate" class="form-control"/>
    </div>

    <div class="mb-3">
      <label class="form-label">Special Notice</label>
      <textarea name="specialNotice" class="form-control" rows="3" required></textarea>
    </div>

    <button type="submit" class="btn btn-primary">Add Service</button>
  </form>
</div>
</body>
</html>

