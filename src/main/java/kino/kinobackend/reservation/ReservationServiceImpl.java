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

        //Either creates a customer if its a new one, or add an existing one to the reservation.
        CustomerModel customer = reservation.getCustomer();
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (customer.getCustomerId() == 0) {
            // if no id is provided, make a new customer
            customer = customerRepository.save(customer);
        } else {
            // if id is provided, find the existing customer
            customer = customerRepository.findById(customer.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
        }
        reservation.setCustomer(customer);

        // ensures that the showing exist in the database.
        ShowingModel showing = null;
        if (reservation.getShowing() != null && reservation.getShowing().getShowingId() > 0) {
            showing = showingRepository.findById(reservation.getShowing().getShowingId())
                    .orElseThrow(() -> new RuntimeException("Showing not found"));
            reservation.setShowing(showing);
        } else {
            throw new RuntimeException("Valid showing ID is required");
        }

        // makes sure that the seats that is added exist
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
        CustomerModel customer = reservation.getCustomer();
        if (customer != null) {
            if (customer.getCustomerId() == 0) {
                // Create new customer
                customer = customerRepository.save(customer);
            } else {
                // Update existing customer or use a different one
                CustomerModel existingCustomer = customerRepository.findById(customer.getCustomerId())
                        .orElseThrow(() -> new RuntimeException("Customer not found with id"));

                // If it's the same customer, update its properties
                if (existingCustomer.getCustomerId() == reservation.getCustomer().getCustomerId()) {
                    existingCustomer.setName(customer.getName());
                    existingCustomer.setEmail(customer.getEmail());
                    existingCustomer.setPhone(customer.getPhone());
                    customer = customerRepository.save(existingCustomer);
                } else {
                    // Using a different customer, no need to update it
                    customer = existingCustomer;
                }
            }
            reservation.setCustomer(customer);
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
    public List<SeatModel> findSeatsByShowingId(int showingId) {

        return reservationRepository.findSeatsByShowingId(showingId);

    }


}
