package roomreservations.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roomreservations.model.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Query to find conflicting reservations when creating a new reservation
    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId AND r.date = :date "
            + "AND r.startTime < :endTime AND r.endTime > :startTime")
    List<Reservation> findConflictingReservations(@Param("roomId") Long roomId,
                                                 @Param("date") LocalDate date,
                                                 @Param("startTime") LocalTime startTime,
                                                 @Param("endTime") LocalTime endTime);
}
