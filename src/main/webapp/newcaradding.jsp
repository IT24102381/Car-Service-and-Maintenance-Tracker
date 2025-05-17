<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Add New Car</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f4f4f4;
      font-family: 'Segoe UI', sans-serif;
    }
    .form-container {
      max-width: 600px;
      margin: 50px auto;
      background: #fff;
      padding: 30px;
      border-radius: 16px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }
    .btn-submit {
      width: 100%;
      padding: 10px;
      border-radius: 12px;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="form-container">
    <h3 class="text-center mb-4">Add a New Car</h3>
    <form action="AddCarServlet" method="post">
      <div class="mb-3">
        <label for="vehicleName" class="form-label">Vehicle Name</label>
        <input type="text" class="form-control" id="vehicleName" name="vehicleName" required>
      </div>
      <div class="mb-3">
        <label for="numberPlate" class="form-label">Number Plate</label>
        <input type="text" class="form-control" id="numberPlate" name="numberPlate" required>
      </div>
      <button type="submit" class="btn btn-primary btn-submit">Add Car</button>
    </form>
  </div>
</div>
</body>
</html>
