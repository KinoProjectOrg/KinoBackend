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

    @Query("SELECT s FROM SeatModel s WHERE s.screen.screenId = :screenId")
    List<SeatModel> findSeatsByScreenId(@Param("screenId") int screenId);



}
