package models;

public class User {

    private String userName;
    private String email;
    private String passwordHash;
    private String role;

    public User() {
    }

    public User(String userName, String email, String passwordHash, String role) {
        this.userName = userName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() { return this.userName; }

    public void setUserName(String userName) { this.userName = userName; }
}
