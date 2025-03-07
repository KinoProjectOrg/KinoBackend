package kino.kinobackend.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kino.kinobackend.customer.CustomerModel;
import kino.kinobackend.seat.SeatModel;
import kino.kinobackend.showing.ShowingModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.ArrayList;
import java.util.List;



@WebMvcTest(ReservationRestController.class)
class ReservationRestControllerTest {

    @MockitoBean
    private ReservationService reservationService;

    @Autowired
    private MockMvc mockMvc;

    ReservationModel reservationModel;
    CustomerModel customer;
    ShowingModel showing;



    @BeforeEach
    public void setUp() {
        reservationModel = new ReservationModel();
        reservationModel.setReservationId(1);

        customer = new CustomerModel();
        customer.setCustomerId(1);
        reservationModel.setCustomer(customer);


        showing = new ShowingModel();
        showing.setShowingId(1);
        reservationModel.setShowing(showing);

        // (empty for simplicity)
        reservationModel.setSeatList(new ArrayList<>());

    }

    @Test
    void getAllReservationTest() throws Exception {

        List<ReservationModel> reservations = new ArrayList<>();
        reservations.add(reservationModel);

        Mockito.when(reservationService.allReservations()).thenReturn(reservations);

        mockMvc.perform(get("/reservation/get")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getReservationByIdTest() throws Exception {

        Mockito.when(reservationService.findReservationById(1)).thenReturn(reservationModel);

        mockMvc.perform(get("/reservation/get/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId").value(1));


    }

    @Test
    void createReservationTest() throws Exception {

        Mockito.when(reservationService.createReservation(Mockito.any(ReservationModel.class)))
                .thenReturn(reservationModel);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(reservationModel);

        mockMvc.perform(post("/reservation/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.reservationId").value(1));

    }

    @Test
    void updateReservationTest() throws Exception {

        Mockito.when(reservationService.updateReservation(Mockito.any(ReservationModel.class)))
                .thenReturn(reservationModel);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(reservationModel);

        mockMvc.perform(put("/reservation/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId").value(1));
    }

    @Test
    void deleteReservationTest() throws Exception {

        Mockito.doNothing().when(reservationService).deleteReservation(1);

        mockMvc.perform(delete("/reservation/delete/1").
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getByShowingIdTest() throws Exception{
        List<SeatModel> seats = new ArrayList<>();
        SeatModel seatModel = new SeatModel();
        seatModel.setSeatId(1);
        seats.add(seatModel);

        Mockito.when(reservationService.findReservedSeatsByShowingId(1)).thenReturn(seats);

        mockMvc.perform(get("/reservation/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

    }

    @Test
    void getByReservationIdTest() throws Exception{
        List<SeatModel> seats = new ArrayList<>();
        SeatModel seatModel = new SeatModel();
        seatModel.setSeatId(1);
        seats.add(seatModel);
        Mockito.when(reservationService.findSeatsByShowingId(1)).thenReturn(seats);

        mockMvc.perform(get("/reservation/seatsInShow/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

    }
}