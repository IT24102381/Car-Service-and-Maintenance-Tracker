package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.controller;

import com.app.carmaintenance.car_maintenance.model.UserModel;
import com.app.carmaintenance.car_maintenance.util.FileUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/DeleteAccountServlet")
public class DeleteAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("UserModel.jsp");
            return;
        }

        UserModel user = (UserModel) session.getAttribute("user");
        String email = user.getUserEmail();

        try {
            FileUtil.deleteUserAccount(email);
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to delete account.");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            return;
        }

        session.invalidate();
        response.sendRedirect("UserModel.jsp");
    }
}
