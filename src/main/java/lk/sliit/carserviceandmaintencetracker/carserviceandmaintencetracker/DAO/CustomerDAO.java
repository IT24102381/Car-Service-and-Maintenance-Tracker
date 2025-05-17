package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.DAO;

import com.app.carmaintenance.car_maintenance.model.CustomerModel;
import com.app.carmaintenance.car_maintenance.util.Config;
import com.app.carmaintenance.car_maintenance.util.FileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public List<CustomerModel> getAllCustomers() {
        List<CustomerModel> customers = new ArrayList<>();
        try {
            List<String> lines = FileUtil.readFile(Config.CUSTOMERS_FILE);
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4 && parts[0].equals("CUSTOMER")) {
                    customers.add(new CustomerModel(parts[1], parts[2], parts[3]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    public CustomerModel getCustomerByEmail(String email) {
        try {
            List<String> lines = FileUtil.readFile(Config.CUSTOMERS_FILE);
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4 && parts[0].equals("CUSTOMER") && parts[2].equalsIgnoreCase(email)) {
                    return new CustomerModel(parts[1], parts[2], parts[3]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveOrUpdateCustomer(CustomerModel customer) {
        try {
            List<String> lines = FileUtil.readFile(Config.CUSTOMERS_FILE);
            List<String> updatedLines = new ArrayList<>();
            boolean found = false;

            // Update existing customer or add new one
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4 && parts[0].equals("CUSTOMER") && parts[2].equalsIgnoreCase(customer.getEmail())) {
                    updatedLines.add("CUSTOMER|" + customer.getName() + "|" + customer.getEmail() + "|" + customer.getPhoneNumber());
                    found = true;
                } else {
                    updatedLines.add(line);
                }
            }

            if (!found) {
                updatedLines.add("CUSTOMER|" + customer.getName() + "|" + customer.getEmail() + "|" + customer.getPhoneNumber());
            }

            // Write all lines back to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Config.CUSTOMERS_FILE))) {
                for (String line : updatedLines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCustomer(String email) {
        try {
            List<String> lines = FileUtil.readFile(Config.CUSTOMERS_FILE);
            List<String> updatedLines = new ArrayList<>();
            boolean found = false;

            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4 && parts[0].equals("CUSTOMER") && parts[2].equalsIgnoreCase(email)) {
                    found = true;
                    continue; // Skip this line (delete)
                }
                updatedLines.add(line);
            }

            if (found) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(Config.CUSTOMERS_FILE))) {
                    for (String line : updatedLines) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}