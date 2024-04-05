package ShuttleProject.Shuttle.DataBases;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="PassengersData")
public class PassengerData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id; // Unique identifier for the passenger

    private String name; // Name of the passenger
    private String address; // Address of the passenger
    private Integer suid; // Unique identifier for a subscription or user ID

    private Integer shuttleid; // Identifier for the shuttle associated with the passenger

    public static List<String> passengersList = new ArrayList<String>();  // Static list to maintain passenger names



    public Integer getShuttleid() {
        return shuttleid;
    }

    public void setShuttleid(Integer shuttleid) {
        this.shuttleid = shuttleid;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSuid() {
        return suid;
    }

    public void setSuid(Integer suid) {
        this.suid = suid;
    }
}
