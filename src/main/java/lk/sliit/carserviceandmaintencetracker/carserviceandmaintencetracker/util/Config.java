package com.app.carmaintenance.car_maintenance.util;

import com.app.carmaintenance.car_maintenance.model.AdminModel;
import jakarta.servlet.ServletContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    public static String DATA_DIR;
    public static String CAR_DATA_DIR;
    public static String APPOINTMENT_DIR;
    public static String USERS_FILE;
    public static String APPOINTMENTS_FILE;
    public static String CUSTOMERS_FILE;

    public static void init(ServletContext context) {

        String webappRoot = context.getRealPath("/");
        System.out.println("WEB APP ROOT: " + webappRoot);

        DATA_DIR = webappRoot + "WEB-INF/data/";
        CAR_DATA_DIR = DATA_DIR + "cars/";
        APPOINTMENT_DIR = DATA_DIR + "appointments/";
        USERS_FILE = DATA_DIR + "users.txt";
        APPOINTMENTS_FILE = APPOINTMENT_DIR + "appointments.txt";
        CUSTOMERS_FILE = DATA_DIR + "customers.txt";

        createDirectoriesAndFiles();
    }

    private static void createDirectoriesAndFiles() {
        createDirectoryIfNotExists(DATA_DIR);
        createDirectoryIfNotExists(CAR_DATA_DIR);
        createDirectoryIfNotExists(APPOINTMENT_DIR);
        createFileIfNotExists(USERS_FILE);
        createFileIfNotExists(APPOINTMENTS_FILE);
        createFileIfNotExists(CUSTOMERS_FILE);
    }

    private static void createDirectoryIfNotExists(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Created directory: " + dir.getAbsolutePath());
        }
    }

    private static void createFileIfNotExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Created file: " + file.getAbsolutePath());

                if (filePath.equals(USERS_FILE)) {
                    addAdminUser(file);
                }
            } catch (IOException e) {
                System.err.println("Failed to create file: " + filePath);
                e.printStackTrace();
            }
        }
    }

    private static void addAdminUser(File usersFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile, true))) {
            AdminModel admin = new AdminModel(
                    "admin@gmail.com",
                    "admin",
                    "admin",
                    "0000000000"
            );
            writer.write(admin.getRole() + "|" +
                    admin.getUserEmail() + "|" +
                    admin.getPassword() + "|" +
                    admin.getUsername() + "|" +
                    admin.getUserMobileNumber());
            writer.newLine();
            System.out.println("Admin user added to users.txt");
        } catch (IOException e) {
            System.err.println("Failed to add admin user");
            e.printStackTrace();
        }
    }

    public static String sanitizeEmail(String email) {
        return email.replaceAll("[^a-zA-Z0-9]", "_");
    }
}