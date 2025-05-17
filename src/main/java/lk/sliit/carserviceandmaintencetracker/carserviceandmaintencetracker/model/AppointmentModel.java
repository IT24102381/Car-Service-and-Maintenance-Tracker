package com.app.carmaintenance.car_maintenance.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentModel {
    private int id;
    private String customerName;
    private String vehicleModel;
    private LocalDateTime appointmentDate;
    private String serviceType;
    private String status;

    public AppointmentModel(int id, String customerName, String vehicleModel, LocalDateTime appointmentDate,
                            String serviceType, String status) {
        this.id = id;
        this.customerName = customerName;
        this.vehicleModel = vehicleModel;
        this.appointmentDate = appointmentDate;
        this.serviceType = serviceType;
        this.status = status;
    }

    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getVehicleModel() { return vehicleModel; }
    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public String getFormattedDate() {
        return appointmentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
    public String getServiceType() { return serviceType; }
    public String getStatus() { return status; }

    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setVehicleModel(String vehicleModel) { this.vehicleModel = vehicleModel; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public void setStatus(String status) { this.status = status; }
}
