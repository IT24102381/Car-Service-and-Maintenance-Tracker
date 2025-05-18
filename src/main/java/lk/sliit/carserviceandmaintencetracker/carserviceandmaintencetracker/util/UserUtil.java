package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.util;

import com.app.carmaintenance.car_maintenance.model.UserModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserUtil {

    public static boolean registerUser(UserModel user) throws IOException {
        if (userExists(user.getUserEmail())) return false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Config.USERS_FILE, true))) {
            // Original format: ROLE|email|password|username|phone
            writer.write(user.getRole() + "|" +
                    user.getUserEmail() + "|" +
                    user.getPassword() + "|" +
                    user.getUsername() + "|" +
                    user.getUserMobileNumber());
            writer.newLine();
            return true;
        }
    }

    public static boolean userExists(String email) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(Config.USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2 && parts[1].equalsIgnoreCase(email)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static UserModel authenticate(String email, String password) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(Config.USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5 &&
                        parts[1].equalsIgnoreCase(email) &&
                        parts[2].equals(password)) {

                    UserModel user = new UserModel();
                    user.setRole(parts[0]);
                    user.setUserEmail(parts[1]);
                    user.setPassword(parts[2]);
                    user.setUsername(parts[3]);
                    user.setUserMobileNumber(parts[4]);
                    return user;
                }
            }
        }
        return null;
    }

    public static boolean deleteUserAccount(String email) throws IOException {
        File inputFile = new File(Config.USERS_FILE);
        File tempFile = new File(Config.USERS_FILE + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2 && parts[1].equalsIgnoreCase(email)) {
                    found = true;
                    continue; // skip this line (don't write it to temp file)
                }
                writer.write(line);
                writer.newLine();
            }

            if (!found) {
                throw new IOException("User not found");
            }
        }

        // Replace original file with temp file
        if (!inputFile.delete()) {
            throw new IOException("Could not delete original file");
        }
        if (!tempFile.renameTo(inputFile)) {
            throw new IOException("Could not rename temp file");
        }

        return true;
    }

    public static List<UserModel> getAllUsers() throws IOException {
        List<UserModel> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(Config.USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    UserModel user = new UserModel();
                    user.setRole(parts[0]);
                    user.setUserEmail(parts[1]);
                    user.setPassword(parts[2]);
                    user.setUsername(parts[3]);
                    user.setUserMobileNumber(parts[4]);
                    users.add(user);
                }
            }
        }
        return users;
    }

    public static List<UserModel> getAllCustomers() throws IOException {
        List<UserModel> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(Config.USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5 && "USER".equalsIgnoreCase(parts[0])) {
                    UserModel user = new UserModel();
                    user.setRole(parts[0]);
                    user.setUserEmail(parts[1]);
                    user.setPassword(parts[2]);
                    user.setUsername(parts[3]);
                    user.setUserMobileNumber(parts[4]);
                    customers.add(user);
                }
            }
        }
        return customers;
    }

    public static boolean deleteCustomer(String email) throws IOException {
        File inputFile = new File(Config.USERS_FILE);
        File tempFile = new File(Config.USERS_FILE + ".tmp");

        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2 && parts[1].equalsIgnoreCase(email)) {
                    found = true;
                    continue; // Skip this line (don't write to temp file)
                }
                writer.write(line);
                writer.newLine();
            }
        }

        if (!found) return false;

        // Replace original file with temp file
        if (!inputFile.delete()) {
            throw new IOException("Could not delete original file");
        }
        if (!tempFile.renameTo(inputFile)) {
            throw new IOException("Could not rename temp file");
        }

        return true;
    }

    public static UserModel getUserByEmail(String email) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(Config.USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5 && parts[1].equalsIgnoreCase(email)) {
                    UserModel user = new UserModel();
                    user.setRole(parts[0]);
                    user.setUserEmail(parts[1]);
                    user.setPassword(parts[2]);
                    user.setUsername(parts[3]);
                    user.setUserMobileNumber(parts[4]);
                    return user;
                }
            }
        }
        return null;
    }
}