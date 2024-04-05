package ShuttleProject.Shuttle.Repositories;

import ShuttleProject.Shuttle.DataBases.PassengerData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengersRepository extends JpaRepository<PassengerData,Integer> {

}
