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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Transactional
    public ReservationModel createReservation(ReservationModel reservationModel) {
        // 1. Handle customer - create if doesn't exist
        CustomerModel customer;

        if (reservationModel.getCustomer().getCustomerId() > 0) {
            // Try to find existing customer
            customer = customerRepository.findById(reservationModel.getCustomer().getCustomerId())
                    .orElse(null); // Use orElse(null) instead of orElseThrow
        } else {
            customer = null; // No customer ID provided
        }

        // If customer not found or no ID provided, create a new one
        if (customer == null) {
            customer = new CustomerModel();
            customer.setUsername(reservationModel.getCustomer().getUsername());
            customer.setPassword(reservationModel.getCustomer().getPassword());
            customer = customerRepository.save(customer);
        }

        // 2. Verify showing exists
        ShowingModel showing = showingRepository.findById(reservationModel.getShowing().getShowingId())
                .orElseThrow(() -> new RuntimeException("Showing not found"));

        // 3. Verify all seats exist and are available
        List<SeatModel> seats = new ArrayList<>();
        for (SeatModel seatModel : reservationModel.getSeatList()) {
            SeatModel seat = seatRepository.findById(seatModel.getSeatId())
                    .orElseThrow(() -> new RuntimeException("Seat not found"));

            // Check if seat is already reserved for this showing
            if (isAlreadyReserved(seat.getSeatId(), showing.getShowingId())) {
                throw new RuntimeException("Seat " + seat.getSeatId() + " is already reserved");
            }

            seats.add(seat);
        }

        // 4. Create and save the reservation
        ReservationModel newReservation = new ReservationModel();
        newReservation.setShowing(showing);
        newReservation.setCustomer(customer);
        newReservation.setSeatList(seats);

        return reservationRepository.save(newReservation);
    }

    private boolean isAlreadyReserved(int seatId, int showingId) {
        // Implement this method to check if the seat is already reserved
        // For example:
        List<SeatModel> reservedSeats = reservationRepository.findReservedSeatsByShowingId(showingId);
        return reservedSeats.stream().anyMatch(seat -> seat.getSeatId() == seatId);
    }


    @Override
    @Transactional
    public ReservationModel updateReservation(ReservationModel reservation) {

        // first we check if reservation exists
        if (!reservationRepository.existsById(reservation.getReservationId())) {
            throw new IllegalArgumentException("Reservation not found");
        }

        // then handle customer - create if doesn't exist
        CustomerModel customer = reservation.getCustomer();
        if (customer != null) {
            if (customer.getCustomerId() == 0) {
                // Create new customer
                customer = customerRepository.save(customer);
            } else {
                // Update existing customer
                CustomerModel existingCustomer = customerRepository.findById(customer.getCustomerId())
                        .orElseThrow(() -> new RuntimeException("Customer not found with id"));
                existingCustomer.setUsername(customer.getUsername());
                existingCustomer.setPassword(customer.getPassword());
                customer = customerRepository.save(existingCustomer);
            }
            reservation.setCustomer(customer);
        }

        // check showing exists
        ShowingModel showing = showingRepository.findById(reservation.getShowing().getShowingId())
                .orElseThrow(() -> new RuntimeException("Showing not found"));

        // checks all seats exist and are available
        List<SeatModel> seats = new ArrayList<>();
        for (SeatModel seatModel : reservation.getSeatList()) {
            SeatModel seat = seatRepository.findById(seatModel.getSeatId())
                    .orElseThrow(() -> new RuntimeException("Seat not found"));

            // Check if seat is already reserved for this showing
            if (isAlreadyReserved(seat.getSeatId(), showing.getShowingId())) {
                throw new RuntimeException("Seat " + seat.getSeatId() + " is already reserved");
            }

            seats.add(seat);
        }

        // update and save the reservation
        reservation.setShowing(showing);
        reservation.setSeatList(seats);

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
    public List<SeatModel> getSeatsForScreenByShowingId(int showingId) {

        // Get the screen from the showing
        ShowingModel showing = showingRepository.findById(showingId)
                .orElseThrow(() -> new IllegalArgumentException("Showing not found with id: " + showingId));

        ScreenModel screen = showing.getScreenModel();

        // Fetch all seats for the screen
        return reservationRepository.findSeatsByScreenId(screen.getScreenId());

    }


}
