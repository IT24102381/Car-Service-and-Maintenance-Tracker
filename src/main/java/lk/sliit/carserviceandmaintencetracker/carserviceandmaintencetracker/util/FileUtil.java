package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.util;

import com.app.carmaintenance.car_maintenance.model.ServiceRecord;
import com.app.carmaintenance.car_maintenance.model.VehicleModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static void appendVehicle(String email, VehicleModel vehicle) throws IOException {
        String sanitizedEmail = Config.sanitizeEmail(email);
        File userFile = new File(Config.CAR_DATA_DIR + sanitizedEmail + ".txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
            writer.write("VEHICLE|" + vehicle.getModel() + "|" + vehicle.getVehicleNumber());
            writer.newLine();
        }
    }

    public static void appendServiceRecord(String email, String vehicleNumber, ServiceRecord record) throws IOException {
        String sanitizedEmail = Config.sanitizeEmail(email);
        File userFile = new File(Config.CAR_DATA_DIR + sanitizedEmail + ".txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
            writer.write("SERVICE|" + vehicleNumber + "|" +
                    record.getDate() + "|" +
                    record.getServiceType() + "|" +
                    record.getMechanic() + "|" +
                    record.getOilRenewDate() + "|" +
                    record.getLastServiceDate() + "|" +
                    record.getAlignmentCheckDate() + "|" +
                    record.getTyreRenewDate() + "|" +
                    record.getSpecialNotice());
            writer.newLine();
        }
    }

    public static List<VehicleModel> readVehiclesFromFile(File userFile) {
        List<VehicleModel> vehicles = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 3 && "VEHICLE".equals(parts[0])) {
                    vehicles.add(new VehicleModel(parts[2].trim(), parts[1].trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public static List<ServiceRecord> readServiceRecords(File userFile, String vehicleNumber) throws IOException {
        List<ServiceRecord> records = new ArrayList<>();

        if (userFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 10 && "SERVICE".equals(parts[0]) && parts[1].equals(vehicleNumber)) {
                        records.add(new ServiceRecord(
                                parts[2], parts[3], parts[4],
                                parts[5], parts[6], parts[7], parts[8], parts[9]
                        ));
                    }
                }
            }
        }
        return records;
    }

    public static void deleteVehicleFromUserFile(String email, String vehicleNumber) throws IOException {
        File userFile = new File(Config.CAR_DATA_DIR + Config.sanitizeEmail(email) + ".txt");
        if (!userFile.exists()) return;

        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!(line.startsWith("VEHICLE") && line.contains(vehicleNumber))) {
                    updatedLines.add(line);
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        }
    }

    public static void deleteServiceFromUserFile(String email, String vehicleNumber, String serviceDate) throws IOException {
        File userFile = new File(Config.CAR_DATA_DIR + Config.sanitizeEmail(email) + ".txt");
        if (!userFile.exists()) return;

        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (!(parts.length >= 10 && "SERVICE".equals(parts[0])
                        && parts[1].equals(vehicleNumber)
                        && parts[2].equals(serviceDate))) {
                    updatedLines.add(line);
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        }
    }

    public static void deleteUserAccount(String email) throws IOException {
        String sanitizedEmail = Config.sanitizeEmail(email);

        // Delete associated car file
        File carFile = new File(Config.CAR_DATA_DIR + sanitizedEmail + ".txt");
        if (carFile.exists()) carFile.delete();

        File inputFile = new File(Config.USERS_FILE);
        File tempFile = new File(Config.DATA_DIR + "users_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split("\\|");
                if (parts.length > 1 && !parts[1].equalsIgnoreCase(email)) {
                    writer.println(currentLine);
                }
            }
        }

        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }

    public static List<String> readFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        }
        return lines;
    }

    public static void writeCustomerDetails(String name, String email, String number) throws IOException {
        String sanitizedEmail = Config.sanitizeEmail(email);
        File customerFile = new File(Config.CUSTOMERS_FILE + sanitizedEmail + ".txt");

        // Create folder if it doesn't exist
        File folder = new File(Config.CUSTOMERS_FILE);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(customerFile, false))) {
            writer.write("CUSTOMER|" + name + "|" + email + "|" + number);
            writer.newLine();
        }
    }

    public static String[] readCustomerDetailsFromFile(File file) throws IOException {
        if (!file.exists()) return null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null && line.startsWith("CUSTOMER")) {
                return line.split("\\|");
            }
        }

        return null;
    }

    public static String[] readCustomerDetails(String email) throws IOException {
        File customerFile = new File(Config.CUSTOMERS_FILE + Config.sanitizeEmail(email) + ".txt");
        if (!customerFile.exists()) return null;

        try (BufferedReader reader = new BufferedReader(new FileReader(customerFile))) {
            String line = reader.readLine();
            if (line != null && line.startsWith("CUSTOMER")) {
                return line.split("\\|");
            }
        }

        return null;
    }



}