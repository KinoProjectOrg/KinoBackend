package kino.kinobackend.reservation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationModel> allReservations() {
        return reservationRepository.findAll();
    }

    public ReservationModel findReservationById(long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with id: " + id));
    }

    public ReservationModel createReservation(ReservationModel reservation) {
        return reservationRepository.save(reservation);
    }

    public ReservationModel updateReservation(ReservationModel reservation) {
        if(!reservationRepository.existsById(reservation.getReservation_id())) {
            throw new IllegalArgumentException("Reservation not found");
        }
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }
}
