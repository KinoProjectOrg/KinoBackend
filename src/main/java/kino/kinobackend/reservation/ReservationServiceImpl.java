package kino.kinobackend.reservation;

import kino.kinobackend.screen.ScreenModel;
import kino.kinobackend.seat.SeatModel;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<ReservationModel> allReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public ReservationModel findReservationById(long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with id: " + id));
    }

    @Override
    public ReservationModel createReservation(ReservationModel reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public ReservationModel updateReservation(ReservationModel reservation) {
        if (!reservationRepository.existsById(reservation.getReservationId())) {
            throw new IllegalArgumentException("Reservation not found");
        }
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(long id) {
        if (!reservationRepository.existsById(id)) {
            throw new IllegalArgumentException("Reservation not found with id: " + id);
        }
        reservationRepository.deleteById(id);
    }

    @Override
    public List<SeatModel> findReservedSeatsByShowingId(@Param("showingId") int showingId) {
        return reservationRepository.findReservedSeatsByShowingId(showingId);
    }

    @Override
    public List<SeatModel> getSeatsForScreenByReservationId(long reservationId) {
        ReservationModel reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with id: " + reservationId));

        // Get the screen from the showing
        ScreenModel screen = reservation.getShowing().getScreen();

        // Fetch all seats for the screen
        return reservationRepository.findSeatsByScreenId(screen.getScreenId());

    }
}
