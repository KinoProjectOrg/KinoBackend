package kino.kinobackend.reservation;

import kino.kinobackend.seat.SeatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationModel, Long> {

    @Query(value = "SELECT * FROM seat_model WHERE seat_id IN " +
            "(SELECT seat_id FROM seat_reservation WHERE reservation_id IN " +
            "(SELECT reservation_id FROM reservation_model WHERE showing_id = :showingId))",
            nativeQuery = true)
    List<SeatModel> findReservedSeatsByShowingId(@Param("showingId") int showingId);

    @Query(value = "SELECT s.* FROM seat_model s " +
            "WHERE s.screen_id = " +
            "(SELECT screen_id FROM showing_model WHERE showing_id = :showingId)",
            nativeQuery = true)
    List<SeatModel> findSeatsByShowingId(@Param("showingId") int showingId);

}
