package kino.kinobackend.seat;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends JpaRepository<SeatModel, Integer> {
    List<SeatModel> findByScreenId(int screenId);

    @Query("SELECT sr.seat.id FROM seat_reservation sr " +
           "JOIN sr.reservation r " +
           "WHERE r.showing.id = :showingId")
    List<Integer> findReservedSeatsIdsByShowingId(@Param("showingId") int showingId);
}
