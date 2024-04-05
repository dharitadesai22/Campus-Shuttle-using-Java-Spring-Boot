package ShuttleProject.Shuttle.Repositories;

import ShuttleProject.Shuttle.DataBases.ShuttleLocation;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ShuttleRepository extends JpaRepository<ShuttleLocation,Integer> {



}
