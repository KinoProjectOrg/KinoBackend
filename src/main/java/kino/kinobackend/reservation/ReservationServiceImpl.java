package kino.kinobackend.reservation;

import kino.kinobackend.customer.CustomerModel;
import kino.kinobackend.customer.CustomerRepository;
import kino.kinobackend.screen.ScreenModel;
import kino.kinobackend.screen.ScreenRepository;
import kino.kinobackend.seat.SeatModel;
import kino.kinobackend.seat.SeatRepository;
import kino.kinobackend.showing.ShowingModel;
import kino.kinobackend.showing.ShowingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final ShowingRepository showingRepository;
    private final CustomerRepository customerRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ShowingRepository showingRepository, SeatRepository seatRepository, CustomerRepository customerRepository) {
        this.reservationRepository = reservationRepository;
        this.showingRepository = showingRepository;
        this.seatRepository = seatRepository;
        this.customerRepository = customerRepository;
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
        CustomerModel customer = reservation.getCustomer();
        if (customer.getCustomerId() == 0) {
            // If no ID is provided, create a new customer
            customer = customerRepository.save(customer);
        } else {
            // If ID is provided, fetch the existing customer
            customer = customerRepository.findById(customer.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
        }
        reservation.setCustomer(customer);

        // Handle Showing
        ShowingModel showing = showingRepository.findById(reservation.getShowing().getShowingId())
                .orElseThrow(() -> new RuntimeException("Showing not found"));
        reservation.setShowing(showing);

        // Handle Seats
        List<SeatModel> seats = reservation.getSeatList().stream()
                .map(seat -> seatRepository.findById(seat.getSeatId())
                        .orElseThrow(() -> new RuntimeException("Seat not found")))
                .toList();
        reservation.setSeatList(seats);

        // Save Reservation
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
