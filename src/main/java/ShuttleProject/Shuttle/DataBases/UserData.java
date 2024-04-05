package ShuttleProject.Shuttle.DataBases;

import javax.persistence.*;

@Entity
// Class representing a User entity in the database
public class UserData {
    @Id
    private Integer SUID; // Unique identifier for the user
    private String name; // Name of the user
    private UserStatus userStatus; // Status of the user (e.g., PICKEDUP, IDLE, WAITING)
    private String address; // Address of the user
    private String email; // Email address of the user

    // Get the unique identifier of the user
    public Integer getSUID() {
        return SUID;
    }

    // Set the unique identifier of the user
    public void setSUID(Integer SUID) {
        this.SUID = SUID;
    }

    // Get the name of the user
    public String getName() {
        return name;
    }

    // Set the name of the user
    public void setName(String name) {
        this.name = name;
    }

    // Get the address of the user
    public String getAddress() {
        return address;
    }

    // Set the address of the user
    public void setAddress(String address) {
        this.address = address;
    }

    // Get the status of the user
    public UserStatus getUserStatus() {
        return userStatus;
    }

    // Set the status of the user
    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    // Get the email address of the user
    public String getEmail() {
        return email;
    }

    // Set the email address of the user
    public void setEmail(String email) {
        this.email = email;
    }
}
