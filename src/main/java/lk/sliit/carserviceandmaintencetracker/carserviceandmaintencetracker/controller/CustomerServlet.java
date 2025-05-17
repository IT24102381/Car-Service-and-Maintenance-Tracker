package     com.app.carmaintenance.car_maintenance.controller;

import com.app.carmaintenance.car_maintenance.dao.CustomerDAO;
import com.app.carmaintenance.car_maintenance.model.CustomerModel;
import com.app.carmaintenance.car_maintenance.model.UserModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        UserModel user = (UserModel) (session != null ? session.getAttribute("user") : null);

        if (user == null) {
            response.sendRedirect("UserModel.jsp");
            return;
        }

        switch (action) {
            case "form":
                String email = request.getParameter("email");
                CustomerModel customer = null;

                if (email != null && !email.isEmpty()) {
                    customer = customerDAO.getCustomerByEmail(email);
                }

                if (customer == null) {
                    customer = new CustomerModel();
                }

                request.setAttribute("customer", customer);
                request.getRequestDispatcher("add-customer.jsp").forward(request, response);
                break;

            case "list":
                request.setAttribute("customers", customerDAO.getAllCustomers());
                request.getRequestDispatcher("customers.jsp").forward(request, response);
                break;

            default:
                response.sendRedirect("dashboard.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        UserModel user = (UserModel) (session != null ? session.getAttribute("user") : null);

        if (user == null) {
            response.sendRedirect("UserModel.jsp");
            return;
        }

        if ("save".equals(action)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            CustomerModel customer = new CustomerModel(name, email, phone);

            if (customerDAO.saveOrUpdateCustomer(customer)) {
                response.sendRedirect("customer?action=list&message=Customer+saved");
            } else {
                response.sendRedirect("customer?action=list&error=Save+failed");
            }
        } else if ("delete".equals(action)) {

            String emailToBeDeleted = request.getParameter("email");
            if (customerDAO.deleteCustomer(emailToBeDeleted)) {
                response.sendRedirect("customer?action=list&message=Customer+deleted+successfully");
            } else {
                response.sendRedirect("customer?action=list&error=Failed+to+delete+customer");
            }
        }
    }
}
