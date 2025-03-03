package kino.kinobackend.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.MDC.get;

@WebMvcTest(ReservationRestController.class)
class ReservationRestControllerTest {

    @MockitoBean
    private ReservationService reservationService;

    @Autowired
    private MockMvc mockMvc;

    ReservationModel reservationModel;

    @BeforeEach
    public void setUp() {
        reservationModel = new ReservationModel();
        reservationModel.setReservation_id(1);

    }

    @Test
    void getAll() {

        List<ReservationModel> reservations = new ArrayList<>();
        reservations.add(reservationModel);

        Mockito.when(reservationService.allReservations()).thenReturn(reservations);

        mockMvc.perform(get("/reservation/get"))
    }

    @Test
    void getById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}