package com.app.carmaintenance.car_maintenance.model;

public class AdminModel extends UserModel {

    public AdminModel() {
        super();
        this.setRole(ROLE_ADMIN); // Automatically set role to ADMIN
    }

    public AdminModel(String userEmail, String password, String username, String userMobileNumber) {
        super(userEmail, password, username, userMobileNumber, ROLE_ADMIN);
    }
}