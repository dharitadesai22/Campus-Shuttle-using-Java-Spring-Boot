package ShuttleProject.Shuttle.Controllers;

import ShuttleProject.Shuttle.Services.CampusShuttleServices;

import ShuttleProject.Shuttle.DataBases.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private CampusShuttleServices campusShuttleServices;

    // Endpoint for adding users into the main database
    @PostMapping(path="/registerUser")
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String address, @RequestParam Integer suid,@RequestParam String email) {
        return  campusShuttleServices.addNewUser(name, address, suid, email);

    }

    // Endpoint for users to request a pickup.
    @PostMapping(path="/requestPickup")
    public @ResponseBody List<String> requestUserPickup(@RequestParam Integer suid){
        return campusShuttleServices.requestUserPickup(suid);
    }

    // Endpoint to get a list of users by their address
    @GetMapping("/userByAddress")
    public @ResponseBody List<UserData> getUserByAddress(@RequestParam String address){
        return campusShuttleServices.getUserByAddress(address);
    }

    // Endpoint to get a list of users by their name
    @GetMapping("/userByName")
    public @ResponseBody List<UserData> getUserByName(@RequestParam String name){
        return campusShuttleServices.getUserByName(name);
    }

    // Endpoint to get a list of all users
    @GetMapping(path="/allUsers")
    public @ResponseBody List<UserData> getAllUsers() {
        return campusShuttleServices.allUsers();
    }

    // Endpoint to retrieve waiting users
    @GetMapping(path="/waitingUsers")
    public @ResponseBody List<UserData> wUser(){
        return campusShuttleServices.waitingUsers();
    }
}
