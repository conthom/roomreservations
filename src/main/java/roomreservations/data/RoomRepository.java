package roomreservations.data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import roomreservations.model.Room;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    // Query to find rooms based on room attributes selected
    @Query("""
        SELECT r FROM Room r
        WHERE
            (:building IS NULL OR r.building ILIKE %:building%) AND
            (:capacity IS NULL OR r.capacity >= :capacity) AND
            (:hasProjector IS NULL OR r.hasProjector = :hasProjector) AND
            (:hasWhiteboard IS NULL OR r.hasWhiteboard = :hasWhiteboard) AND
            (:hasWindows IS NULL OR r.hasWindows = :hasWindows)
        """)
    List<Room> findRooms(
            @Param("building") String building,
            @Param("capacity") Integer capacity,
            @Param("hasProjector") Boolean hasProjector,
            @Param("hasWhiteboard") Boolean hasWhiteboard,
            @Param("hasWindows") Boolean hasWindows
    );
}
