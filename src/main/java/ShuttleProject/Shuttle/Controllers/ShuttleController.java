package ShuttleProject.Shuttle.Controllers;

import ShuttleProject.Shuttle.DataBases.PassengerData;
import ShuttleProject.Shuttle.Services.CampusShuttleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ShuttleController {

    @Autowired
    private CampusShuttleServices campusShuttleServices;


    // Endpoint to add a passenger when a pickup request is made.
    // Sets user status as "waiting" indicating a pickup request.
    // Passengers are added based on estimated time of arrival (ETA) and available seats in a Shuttle.
    @PostMapping(path = "/addPassenger")
    public @ResponseBody String addPassengerForPickup(@RequestParam Integer suid, @RequestParam String address){
        return campusShuttleServices.addPassengerForPickup(suid,address);
    }

    // Endpoint to drop off a passenger.
    @PostMapping(path = "/dropPassenger")
    public @ResponseBody String dropOffPassenger(){
        return campusShuttleServices.dropOffPassenger();
    }

    // Endpoint for a shuttle to update its location.
    @PostMapping(path="/shuttleLocation")
    public @ResponseBody String addShuttleData(@RequestParam double longitude,@RequestParam double latitude){
        return campusShuttleServices.updateShuttleLocation(longitude, latitude);
    }

    // Endpoint to get the current location of the shuttle.
    @GetMapping("/currentShuttleLocation")
    public String getShuttleLocation(){
        return campusShuttleServices.shuttleStatus();
    }

    // Endpoint to retrieve a list of all passengers.
    @GetMapping(path="/allpassengers")
    public @ResponseBody List<PassengerData> getallpassengers(){
        return campusShuttleServices.allPassengers();
    }
}
