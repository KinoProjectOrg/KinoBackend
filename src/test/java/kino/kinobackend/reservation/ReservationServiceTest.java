package kino.kinobackend.reservation;

import kino.kinobackend.customer.CustomerModel;
import kino.kinobackend.customer.CustomerRepository;
import kino.kinobackend.screen.ScreenModel;
import kino.kinobackend.seat.SeatModel;
import kino.kinobackend.seat.SeatRepository;
import kino.kinobackend.showing.ShowingModel;
import kino.kinobackend.showing.ShowingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    ShowingRepository showingRepository;

    @Mock
    SeatRepository seatRepository;

    @InjectMocks
    ReservationServiceImpl reservationService;

    ReservationModel reservationModel;
    CustomerModel customer;
    ShowingModel showing;
    ScreenModel screenModel;
    SeatModel seat1;
    SeatModel seat2;
    //Reservation has need for alot of data when run, so ive tried to implement it in setup.
    @BeforeEach
    void setUp() {
        reservationModel = new ReservationModel();
        reservationModel.setReservationId(1L);

        customer = new CustomerModel();
        customer.setCustomerId(1L);
        reservationModel.setCustomer(customer);

        screenModel = new ScreenModel();
        screenModel.setScreenId(1);

        showing = new ShowingModel();
        showing.setShowingId(1);
        showing.setScreenModel(screenModel);

        seat1 = new SeatModel();
        seat1.setSeatId(1);
        seat2 = new SeatModel();
        seat2.setSeatId(2);
        List<SeatModel> seats = new ArrayList<>();
        seats.add(seat1);
        seats.add(seat2);

        reservationModel.setShowing(showing);

        // (empty for simplicity)
        reservationModel.setSeatList(new ArrayList<>());
    }

    @Test
    void allReservationsTest() {
        List<ReservationModel> list = new ArrayList<>();
        list.add(reservationModel);
        Mockito.when(reservationRepository.findAll()).thenReturn(list);

        List<ReservationModel> foundReservations = reservationService.allReservations();

        assertEquals(list.size(), foundReservations.size());
        assertEquals(reservationModel, foundReservations.get(0));
    }

    @Test
    void findReservationByIdTest() {
        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservationModel));

        ReservationModel foundReservation = reservationService.findReservationById(1L);

        assertEquals(reservationModel, foundReservation);
    }

    @Test
    void createReservationTest() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Mockito.when(showingRepository.findById(1)).thenReturn(Optional.of(showing));
        Mockito.when(reservationRepository.save(Mockito.any(ReservationModel.class))).thenReturn(reservationModel);

        // Call the method under test
        ReservationModel createdReservation = reservationService.createReservation(reservationModel);

        // Assertions
        assertEquals(reservationModel, createdReservation);

        // Verify interactions
        Mockito.verify(customerRepository).findById(1L);
        Mockito.verify(showingRepository).findById(1);
        Mockito.verify(reservationRepository).save(Mockito.any(ReservationModel.class));
    }

    @Test
    void updateReservationTest() {
        // Setup mocks for the repositories
        Mockito.when(reservationRepository.existsById(1L)).thenReturn(true);
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Mockito.when(customerRepository.save(Mockito.any(CustomerModel.class))).thenReturn(customer);
        Mockito.when(showingRepository.findById(1)).thenReturn(Optional.of(showing));
        Mockito.when(reservationRepository.save(Mockito.any(ReservationModel.class))).thenReturn(reservationModel);

        // Execute the method
        ReservationModel updatedReservation = reservationService.updateReservation(reservationModel);

        // Verify the result
        assertEquals(reservationModel, updatedReservation);

    }

    @Test
    void deleteReservationTest() {

        Mockito.when(reservationRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(reservationRepository).deleteById(1L);

        reservationService.deleteReservation(1L);

        Mockito.verify(reservationRepository).deleteById(1L);
    }

    @Test
    void findReservedSeatByShowingIdTest(){

        Mockito.when(reservationRepository.findReservedSeatsByShowingId(1)).thenReturn(reservationModel.getSeatList());

        List<SeatModel> seats = reservationService.findReservedSeatsByShowingId(1);

        assertEquals(reservationModel.getSeatList().size(), seats.size());
    }

    @Test
    void getSeatsForScreenByReservationIdTest(){
        Mockito.when(showingRepository.findById(1)).thenReturn(Optional.of(showing));
        Mockito.when(reservationRepository.findSeatsByScreenId(1)).thenReturn(reservationModel.getSeatList());

        List<SeatModel> seats = reservationService.getSeatsForScreenByShowingId(1);

        assertEquals(reservationModel.getSeatList().size(), seats.size());
    }
}
