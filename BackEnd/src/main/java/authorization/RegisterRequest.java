package authorization;

public class RegisterRequest {

    private String email;
    private String userName;
    private String password;
    private String role;

    public RegisterRequest() {
    }

    public RegisterRequest(String email, String userName, String password, String role) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
