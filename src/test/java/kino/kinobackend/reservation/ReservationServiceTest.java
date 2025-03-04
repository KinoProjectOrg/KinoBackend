package kino.kinobackend.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jdk.jfr.internal.jfc.model.Constraint.any;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ReservationServiceTest {

    @Mock
    ReservationRepository reservationRepository;

    @InjectMocks
    ReservationService reservationService;

    ReservationModel reservationModel;
    @BeforeEach
    void setUp() {
        reservationModel = new ReservationModel();
        reservationModel.setReservation_id(1);
    }

    @Test
    void allReservations() {
        List<ReservationModel> list = new ArrayList<>();
        list.add(reservationModel);
        Mockito.when(reservationRepository.findAll()).thenReturn(list);

        List<ReservationModel> foundReservation = reservationRepository.findAll();

        assertEquals(foundReservation.size(), 1);

    }

    @Test
    void findReservationById() {

        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservationModel));

        ReservationModel foundReservation = reservationService.findReservationById(1L);
        assertEquals(foundReservation.getReservation_id(), 1);
    }

    @Test
    void createReservation() {

        Mockito.when(reservationRepository.save(reservationModel)).thenReturn(reservationModel);

        ReservationModel foundReservation = reservationService.createReservation(reservationModel);

        assertEquals(foundReservation.getReservation_id(), 1);

    }

    @Test
    void updateReservation() {

        Mockito.when(reservationRepository.save(reservationModel)).thenReturn(reservationModel);

        ReservationModel foundReservation = reservationService.updateReservation(reservationModel);

        assertEquals(foundReservation.getReservation_id(), 1);
    }

    @Test
    void deleteReservation() {
        Mockito.doNothing().when(reservationRepository).deleteById(1L);

        reservationService.deleteReservation(1L);

        Mockito.verify(reservationRepository, Mockito.times(1)).deleteById(1L);

        assertNull(reservationRepository.findById(1L));
    }
}