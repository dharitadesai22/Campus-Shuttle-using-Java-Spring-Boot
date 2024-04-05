package ShuttleProject.Shuttle.Repositories;

import ShuttleProject.Shuttle.DataBases.UserData;
import ShuttleProject.Shuttle.DataBases.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface UsersRepository extends JpaRepository<UserData, Integer> {


    List<UserData> findUserBySUID(Integer suid);
    List<UserData> findUserByAddress(String address);

    List<UserData> findUserByName(String name);
    UserData findBySUID(Integer SUID);

    UserData findByEmail(String email);

    List<UserData> findByUserStatus(UserStatus userStatus);


}

