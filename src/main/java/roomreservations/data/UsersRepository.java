package roomreservations.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roomreservations.model.Reservation;
import roomreservations.model.Users;


public interface UsersRepository extends JpaRepository<Users, Long>{
    @Query("SELECT u FROM Users u WHERE u.username = :username AND u.password = :password")
    Users findByUsernameAndPassword(@Param("username") String username,
                                    @Param("password") String password);
}

