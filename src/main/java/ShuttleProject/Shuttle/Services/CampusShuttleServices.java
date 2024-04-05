package ShuttleProject.Shuttle.Services;

import ShuttleProject.Shuttle.Repositories.PassengersRepository;
import ShuttleProject.Shuttle.Repositories.ShuttleRepository;
import ShuttleProject.Shuttle.Repositories.UsersRepository;
import ShuttleProject.Shuttle.DataBases.ShuttleLocation;
import ShuttleProject.Shuttle.DataBases.UserData;
import ShuttleProject.Shuttle.DataBases.UserStatus;
import ShuttleProject.Shuttle.DataBases.PassengerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.*;


@Service
public class CampusShuttleServices {

    @Autowired
    private ShuttleRepository shuttleRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PassengersRepository passengersRepository;

    private Queue<PassengerData> passengerDataQueue = new LinkedList<>();


    // We are defining the minimum and maximum possible ETAs - Estimated times of arrival for our shuttle
    int minimum = 10; // This defines the min ETA (in minutes)
    int maximum = 100; // This defines the max ETA (in minutes)

    //This method consists of all the valid user information
    public String addNewUser(String name, String address, Integer suid, String email){
        if (!isValidSUID(suid)) {
            return "PLEASE INPUT A VALID 9-DIGIT SUID NUMBER";
        }

        // Check if SUID already exists
        if (usersRepository.findBySUID(suid) != null) {
            return "ERROR! SUID ALREADY EXISTS. PLEASE ENTER AGAIN";
        }

        UserData u = new UserData();
        u.setSUID(suid);
        u.setName(name);
        u.setAddress(address);

        // Checking if the email of the student is valid
        if (!isValidEmail(email)) {
            return "PLEASE ENTER A VALID EMAIL ID";
        }

        // Checking if the email os the student is already existing
        if (usersRepository.findByEmail(email) != null) {
            return "EMAIL ALREADY EXISTS. PLEASE ENTER A VALID EMAIL ID";
        }

        u.setEmail(email);
        u.setUserStatus(UserStatus.IDLE);
        usersRepository.save(u);

        return "USER SUCCESSFULLY ADDED!";
    }

    private boolean isValidSUID(Integer suid) {
        String suidStr = suid.toString();
        // Check if the SUID is exactly 9 digits and contains only numeric characters
        return suidStr.length() == 9 && suidStr.matches("\\d+");
    }

    private boolean isValidEmail(String email) {
        return email.endsWith("syr.edu");
    }


    // We are considering the following constraints for a User to request a pickup
    // User is a valid user - his SUID is present in the Database
    // User's status should be IDLE.
    // And the User is already pickup up, he cannot request another pickup
    public List<String> requestUserPickup(Integer suid){
        UserData userData = usersRepository.findBySUID(suid);
        PassengerData.passengersList.add(userData.getName());

        List<String> response = new ArrayList<>(); // Response messages list
        if (userData != null) { // Check if user exists
            if (userData.getUserStatus() == UserStatus.IDLE) { // Check if user is IDLE
                if (userData.getUserStatus() == UserStatus.PICKEDUP) { // Check if the user is already picked up
                    response.add("YOU ARE ALREADY IN YOUR RIDE, HENCE YOU CANNOT REQUEST ANOTHER RIDE");
                } else if (userData.getUserStatus() == UserStatus.WAITING) { // Check if the user has already made a pick up request
                    response.add("YOU HAVE ALREADY MADE A PICK-UP REQUEST");
                } else {
                    userData.setUserStatus(UserStatus.WAITING); // Set user status to WAITING
                    usersRepository.save(userData); // Save user status
                    // Calculate and assign ETA for the ride
                    double randomValue = Math.random() * (maximum - minimum + 1) + minimum;
                    String eta = String.format("%.2f", randomValue);
                    response.add("YOUR ETA FOR THE SHUTTLE IS " + eta + " mins");
                }
            } else {
                response.add("USER STATUS IS NOT IDLE");
            }
        } else {
            response.add("PLEASE ENTER A VALID SUID");
        }

        return response; // Return response messages
    }


    // We are considering the following constraints for a User to be picked up
    // User's status should be WAITING.
    public String addPassengerForPickup(Integer suid, String address){
        // Retrieve user information by SUID
        UserData us= usersRepository.findBySUID(suid);

        // Get reference to Shuttle Location with ID 1
        ShuttleLocation s1 = shuttleRepository.getReferenceById(1);

        // Create a new Passenger object
        PassengerData p = new PassengerData();

        // Check the status of the user and process accordingly
        if (us.getUserStatus() == UserStatus.WAITING) {
            // Setting passenger details
            p.setSuid(suid);
            p.setAddress(address);
            p.setName(us.getName());

            // Check if there are available seats in the shuttle
            if (s1.getSeats() == null || s1.getSeats() <= 15) {
                // Increment the number of seats and assign the shuttle ID to the passenger
                s1.setSeats(s1.getSeats() + 1);
                p.setShuttleid(s1.getSid());

                // Update user and passenger status, and save details
                us.setUserStatus(UserStatus.PICKEDUP);
                passengersRepository.save(p);
                usersRepository.save(us);

                // Add the passenger to the queue and return a success message
                passengerDataQueue.offer(p);

                return "PASSENGER NAMED " + us.getName() + " WITH SUID " + suid + " IS PICKED UP SUCCESSFULLY";
            } else {
                    return "SORRY! NO SEATS ARE AVAILABLE IN THE SHUTTLE.";
            }

        } else if (us.getUserStatus() == UserStatus.PICKEDUP) {
                return "PASSENGER IS ALREADY ON THE BUS";
        } else if (us.getUserStatus() == UserStatus.IDLE) {
                return "PASSENGER HAS TO RAISE A PICK-UP REQUEST BEFORE";
        }
        return "PASSENGER NOT ADDED";
    }

    //Passengers will be dropped off and the shuttle returns to the campus stop
    public String dropOffPassenger() {
        ShuttleLocation s1 = shuttleRepository.getReferenceById(1);

        if (passengerDataQueue.isEmpty()) {
            return "SHUTTLE IS WAITING AT THE STOP.";
        } else {
            // Loop through all passengers in the queue
            while (!passengerDataQueue.isEmpty()) {
                // Drop off the passengers in the order they were added to the queue
                PassengerData nextPassengerData = passengerDataQueue.poll();

                // Update the user status to IDLE
                UserData userData = usersRepository.findBySUID(nextPassengerData.getSuid());

                if (userData != null) {
                    userData.setUserStatus(UserStatus.IDLE);
                    s1.setSeats(s1.getSeats() - 1);
                    usersRepository.save(userData);
                }
                passengersRepository.delete(nextPassengerData);

                // Log the dropped off passenger
                return "SHUTTLE IS DROPPING OFF PASSENGER NAMED " + nextPassengerData.getName() + " AT " + nextPassengerData.getAddress() + ".";
            }
        }

        return "SHUTTLE IS WAITING AT THE STOP"; // Default return statement if queue is empty
    }


    //To update the shuttle location
    public String updateShuttleLocation(double longitude, double latitude){
        // Fetching the only available shuttle
        ShuttleLocation sl=shuttleRepository.getReferenceById(1); //We have only one shuttle
        sl.setLongitude(longitude);
        sl.setLatitude(latitude);
        shuttleRepository.save(sl);
        return "UPDATED SHUTTLE LOCATION!";
    }

    //To retrieve the current shuttle location
    public String shuttleStatus() {
        ShuttleLocation sl = new ShuttleLocation();
        return ("SHUTTLE IS CURRENTLY LOCATED AT: (LONGITUDE: " + sl.getLongitude() + ", LATITUDE: " + sl.getLatitude() + ").");
    }

    public List<UserData> waitingUsers(){
        List<UserData> waitingUserData = usersRepository.findByUserStatus(UserStatus.WAITING);
        return waitingUserData;
    }

    public List<UserData> getUserByAddress(String address) {
        // Call the userRepository method to find users by address
        return retrieveUsersByAddress(address);
    }

    // Refactored method to retrieve users by address using the repository
    private List<UserData> retrieveUsersByAddress(String address) {
        // Use userRepository to find users by the specified address
        return usersRepository.findUserByAddress(address);
    }

    public List<UserData> getUserByName(String name) {
        // Retrieve users by name using userRepository
        return fetchUsersByName(name);
    }

    // Renamed method for readability
    private List<UserData> fetchUsersByName(String name) {
        // Utilize userRepository to find users by name
        return usersRepository.findUserByName(name);
    }


    // Retrieves a list of all users from the repository.
    public List<UserData> allUsers(){
        return usersRepository.findAll();
    }

    // Retrieves a list of all passengers for shuttle services.
    public List<PassengerData> allPassengers(){

        return passengersRepository.findAll();
    }
}
