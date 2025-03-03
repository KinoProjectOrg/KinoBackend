package kino.kinobackend.reservation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationModel> allReservations() {
        return reservationRepository.findAll();
    }

    public ReservationModel findReservationById(long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public ReservationModel createReservation(ReservationModel reservation) {
        return reservationRepository.save(reservation);
    }

    public ReservationModel updateReservation(ReservationModel reservation) {
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
