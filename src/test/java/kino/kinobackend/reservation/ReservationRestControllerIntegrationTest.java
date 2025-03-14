package kino.kinobackend.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kino.kinobackend.customer.CustomerModel;
import kino.kinobackend.customer.CustomerRepository;
import kino.kinobackend.movie.MovieModel;
import kino.kinobackend.movie.MovieRepository;
import kino.kinobackend.screen.ScreenModel;
import kino.kinobackend.screen.ScreenRepository;
import kino.kinobackend.seat.SeatModel;
import kino.kinobackend.seat.SeatRepository;
import kino.kinobackend.showing.ShowingModel;
import kino.kinobackend.showing.ShowingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class ReservationRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShowingRepository showingRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ReservationModel reservationModel;
    private ReservationModel reservationModel2;

    @BeforeEach
    void setup() throws Exception {
        // Configure ObjectMapper
        objectMapper = new ObjectMapper();

        // Clear existing data
        reservationRepository.deleteAll();
        showingRepository.deleteAll();
        customerRepository.deleteAll();
        movieRepository.deleteAll();
        screenRepository.deleteAll();
        seatRepository.deleteAll();

        // Create and save movies first
        MovieModel movie1 = new MovieModel();
        movie1.setTitle("Test Movie 1");
        movieRepository.save(movie1);

        // Create and save screens
        ScreenModel screen1 = new ScreenModel();
        screen1.setScreenNumber(1);
        screenRepository.save(screen1);

        ScreenModel screen2 = new ScreenModel();
        screen2.setScreenNumber(2);
        screenRepository.save(screen2);

        // Create and save showings
        ShowingModel showing1 = new ShowingModel();
        showing1.setMovieModel(movie1);
        showing1.setScreenModel(screen1);
        showingRepository.save(showing1);

        ShowingModel showing2 = new ShowingModel();
        showing2.setMovieModel(movie1);
        showing2.setScreenModel(screen2);
        showingRepository.save(showing2);

        // Create and save customers
        CustomerModel customer1 = new CustomerModel();
        customer1.setUsername("Customer1");
        customer1.setPassword("password1");
        // Set other required fields for customer
        customer1 = customerRepository.save(customer1);

        CustomerModel customer2 = new CustomerModel();
        customer2.setUsername("Customer2");
        customer2.setPassword("password2");
        // Set other required fields for customer
        customer2 = customerRepository.save(customer2);

        SeatModel seatModel1 = new SeatModel();
        SeatModel seatModel2 = new SeatModel();
        List<SeatModel> list = new ArrayList<>();
        list.add(seatModel1);
        list.add(seatModel2);

        SeatModel seatModel3 = new SeatModel();
        SeatModel seatModel4 = new SeatModel();
        List<SeatModel> list1 = new ArrayList<>();
        list1.add(seatModel3);
        list1.add(seatModel4);

        // Create and save reservations
        reservationModel = new ReservationModel();
        reservationModel.setCustomer(customer1);
        reservationModel.setShowing(showing1);
        reservationModel.setSeatList(list);
        reservationRepository.save(reservationModel);

        reservationModel2 = new ReservationModel();
        reservationModel2.setCustomer(customer2);
        reservationModel2.setShowing(showing2);
        reservationModel2.setSeatList(list1);
        reservationRepository.save(reservationModel2);
        seatRepository.save(seatModel1);
        seatRepository.save(seatModel2);
        seatRepository.save(seatModel3);
        seatRepository.save(seatModel4);
    }

    @Test
    void getAllReservations() throws Exception {
        mockMvc.perform(get("/reservation/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getReservationById() throws Exception {
        mockMvc.perform(get("/reservation/get/" + reservationModel.getReservationId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId").value(reservationModel.getReservationId()));
    }


    @Test
    void createReservation() throws Exception {
        // Create a new reservation with different seats
        ReservationModel newReservation = new ReservationModel();
        newReservation.setCustomer(reservationModel2.getCustomer());
        newReservation.setShowing(reservationModel2.getShowing());

        // Create new seats that aren't already reserved
        SeatModel newSeat1 = new SeatModel();
        SeatModel newSeat2 = new SeatModel();
        seatRepository.save(newSeat1);
        seatRepository.save(newSeat2);

        List<SeatModel> newSeatList = new ArrayList<>();
        newSeatList.add(newSeat1);
        newSeatList.add(newSeat2);
        newReservation.setSeatList(newSeatList);

        String createdReservation = objectMapper.writeValueAsString(newReservation);

        mockMvc.perform(post("/reservation/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createdReservation))
                .andExpect(status().isCreated());
    }

    @Test
    void updateReservation() throws Exception {
        ReservationModel newReservation = new ReservationModel();
        newReservation.setCustomer(reservationModel2.getCustomer());
        newReservation.setShowing(reservationModel2.getShowing());
        SeatModel newSeat1 = new SeatModel();
        SeatModel newSeat2 = new SeatModel();
        seatRepository.save(newSeat1);
        seatRepository.save(newSeat2);
        List<SeatModel> newSeatList = new ArrayList<>();
        newSeatList.add(newSeat1);
        newSeatList.add(newSeat2);
        newReservation.setSeatList(newSeatList);

        String updatedReservation = objectMapper.writeValueAsString(newReservation);

        mockMvc.perform(put("/reservation/update/" + reservationModel.getReservationId())
        .contentType(MediaType.APPLICATION_JSON)
                .content(updatedReservation))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId").value(reservationModel.getReservationId()));
    }

    @Test
    void deleteReservation() throws Exception {
        mockMvc.perform(delete("/reservation/delete/" + reservationModel.getReservationId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getByReservationId() throws Exception {

        List<ReservationModel> list = reservationRepository.findAll();

        mockMvc.perform(get("/reservation/get/" + reservationModel.getReservationId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(list.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId").value(reservationModel.getReservationId()));
    }

    @Test
    void getByReservationIdTest() throws Exception {
        List<SeatModel> list = seatRepository.findAll();

        mockMvc.perform(get("/reservation/seatsInShow/" + reservationModel.getReservationId())
        .contentType(MediaType.APPLICATION_JSON).content(list.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
