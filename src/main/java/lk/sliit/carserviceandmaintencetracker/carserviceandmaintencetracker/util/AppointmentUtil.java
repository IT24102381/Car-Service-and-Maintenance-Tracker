package com.app.carmaintenance.car_maintenance.util;

import com.app.carmaintenance.car_maintenance.model.AppointmentModel;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AppointmentUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static List<AppointmentModel> readAppointments() throws IOException {
        List<AppointmentModel> appointments = new ArrayList<>();
        File file = new File(Config.APPOINTMENTS_FILE);
        if (!file.exists()) return appointments;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    appointments.add(new AppointmentModel(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            parts[2],
                            LocalDateTime.parse(parts[3], formatter),
                            parts[4],
                            parts[5]
                    ));
                }
            }
        }
        return appointments;
    }

    public static void saveAppointments(List<AppointmentModel> appointments) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Config.APPOINTMENTS_FILE))) {
            for (AppointmentModel appt : appointments) {
                writer.write(appt.getId() + "|" +
                        appt.getCustomerName() + "|" +
                        appt.getVehicleModel() + "|" +
                        appt.getAppointmentDate().format(formatter) + "|" +
                        appt.getServiceType() + "|" +
                        appt.getStatus());
                writer.newLine();
            }
        }
    }

    public static int generateNewId(List<AppointmentModel> appointments) {
        return appointments.stream().mapToInt(AppointmentModel::getId).max().orElse(0) + 1;
    }

    public static AppointmentModel getById(List<AppointmentModel> appointments, int id) {
        for (AppointmentModel a : appointments) {
            if (a.getId() == id) return a;
        }
        return null;
    }
}