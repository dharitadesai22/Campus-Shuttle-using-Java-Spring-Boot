package ShuttleProject.Shuttle.DataBases;

import javax.persistence.*;

@Entity
@Table(name = "shuttleloc")
public class ShuttleLocation {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id; // Unique identifier for the shuttle location

    private Integer sid; // Unique identifier for the shuttle

    private double latitude; // Latitude of the shuttle's location
    private double longitude; // Longitude of the shuttle's location

    // Maximum number of seats in a shuttle is 15
    private Integer Seats; // Number of available seats in the shuttle


    public Integer getSeats() {
        return Seats;
    }

    public void setSeats(Integer seats) {
        Seats = seats;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
