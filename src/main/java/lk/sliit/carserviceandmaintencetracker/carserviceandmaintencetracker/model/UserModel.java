package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.model;

public class UserModel {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    private String UserEmail;
    private String Username;
    private String UserMobileNumber;
    private String Password;
    private String role = ROLE_USER;

    public UserModel() {
    }

    public UserModel(String userEmail, String username, String userMobileNumber) {
        this.UserEmail = userEmail;
        this.Username = username;
        this.UserMobileNumber = userMobileNumber;
    }

    public UserModel(String userEmail, String password, String username, String userMobileNumber) {
        this.UserEmail = userEmail;
        this.Password = password;
        this.Username = username;
        this.UserMobileNumber = userMobileNumber;
    }

    public UserModel(String userEmail, String password, String username, String userMobileNumber, String role) {
        this.UserEmail = userEmail;
        this.Password = password;
        this.Username = username;
        this.UserMobileNumber = userMobileNumber;
        this.role = role != null ? role : "USER"; // Default to USER if role is null
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        this.UserEmail = userEmail;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getUserMobileNumber() {
        return UserMobileNumber;
    }

    public void setUserMobileNumber(String userMobileNumber) {
        this.UserMobileNumber = userMobileNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}
