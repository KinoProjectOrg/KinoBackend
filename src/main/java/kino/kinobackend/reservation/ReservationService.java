package kino.kinobackend.reservation;

import kino.kinobackend.seat.SeatModel;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationService {
    List<ReservationModel> allReservations();
    ReservationModel findReservationById(long id);
    ReservationModel createReservation(ReservationModel reservation);
    ReservationModel updateReservation(ReservationModel reservation);
    void deleteReservation(long id);
    List<SeatModel> findReservedSeatsByShowingId(int showingId);
    List<SeatModel> getSeatsForScreenByReservationId(long reservationId);

}
