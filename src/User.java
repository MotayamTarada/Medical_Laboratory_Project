public class User {
    private int userID;
    private String username;
    private String email;
    private String phone;
    private String userType;

    public User(int userID, String username, String email, String phone, String userType) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
