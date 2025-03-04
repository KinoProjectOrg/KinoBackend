package kino.kinobackend.reservation;

import java.util.List;

public interface ReservationService {
    List<ReservationModel> allReservations();
    ReservationModel findReservationById(long id);
    ReservationModel createReservation(ReservationModel reservation);
    ReservationModel updateReservation(ReservationModel reservation);
    void deleteReservation(long id);
}
