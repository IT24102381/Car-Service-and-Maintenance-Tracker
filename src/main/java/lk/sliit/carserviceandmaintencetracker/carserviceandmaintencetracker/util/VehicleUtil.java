package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.util;

import lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.model.VehicleModel;
import lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.model.ServiceRecord;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleUtil {

    public static class Pair<A, B> {
        private final A first;
        private final B second;

        public Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }

        public A getFirst() { return first; }
        public B getSecond() { return second; }
    }

    public static void deleteVehicle(String targetUserEmail, String vehicleNumber) throws IOException {
        String sanitizedEmail = Config.sanitizeEmail(targetUserEmail);
        File userFile = new File(Config.CAR_DATA_DIR + sanitizedEmail + ".txt");

        if (!userFile.exists()) {
            throw new IOException("User file not found");
        }

        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (!(parts.length >= 3 && "VEHICLE".equals(parts[0]) && parts[2].trim().equals(vehicleNumber))) {
                    updatedLines.add(line);
                }
            }
        }

        writeLinesToFile(userFile, updatedLines);
    }

    public static void deleteServiceRecord(String targetUserEmail, String vehicleNumber, String serviceDate)
            throws IOException {
        String sanitizedEmail = Config.sanitizeEmail(targetUserEmail);
        File userFile = new File(Config.CAR_DATA_DIR + sanitizedEmail + ".txt");

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

        writeLinesToFile(userFile, updatedLines);
    }

    public static List<Pair<VehicleModel, String>> getAllVehiclesWithOwners() throws IOException {
        List<Pair<VehicleModel, String>> vehiclesWithOwners = new ArrayList<>();
        File dataDir = new File(Config.CAR_DATA_DIR);

        File[] userFiles = dataDir.listFiles((dir, name) -> name.endsWith(".txt"));
        if (userFiles == null) return vehiclesWithOwners;

        for (File userFile : userFiles) {
            String email = userFile.getName()
                    .replace(".txt", "")
                    .replace("_", "@");

            try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 3 && "VEHICLE".equals(parts[0])) {
                        VehicleModel vehicle = new VehicleModel(parts[2].trim(), parts[1].trim());
                        vehiclesWithOwners.add(new Pair<>(vehicle, email));
                    }
                }
            }
        }
        return vehiclesWithOwners;
    }

    public static List<ServiceRecord> getServicesForVehicle(String userEmail, String vehicleNumber)
            throws IOException {
        List<ServiceRecord> services = new ArrayList<>();
        String sanitizedEmail = Config.sanitizeEmail(userEmail);
        File userFile = new File(Config.CAR_DATA_DIR + sanitizedEmail + ".txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 10 && "SERVICE".equals(parts[0])
                        && parts[1].equals(vehicleNumber)) {
                    services.add(new ServiceRecord(
                            parts[2], parts[3], parts[4], parts[5],
                            parts[6], parts[7], parts[8], parts[9]
                    ));
                }
            }
        }
        return services;
    }

    private static void writeLinesToFile(File file, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}