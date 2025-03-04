package kino.kinobackend.reservation;

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

    @InjectMocks
    ReservationServiceImpl reservationService;

    ReservationModel reservationModel;

    @BeforeEach
    void setUp() {
        reservationModel = new ReservationModel();
        reservationModel.setReservationId(1L);
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
        Mockito.when(reservationRepository.save(reservationModel)).thenReturn(reservationModel);

        ReservationModel createdReservation = reservationService.createReservation(reservationModel);

        assertEquals(reservationModel, createdReservation);
        Mockito.verify(reservationRepository).save(reservationModel);
    }

    @Test
    void updateReservationTest() {
        Mockito.when(reservationRepository.existsById(1L)).thenReturn(true);
        Mockito.when(reservationRepository.save(reservationModel)).thenReturn(reservationModel);

        ReservationModel updatedReservation = reservationService.updateReservation(reservationModel);

        assertEquals(reservationModel, updatedReservation);
        Mockito.verify(reservationRepository).save(reservationModel);
    }


    @Test
    void deleteReservationTest() {

        Mockito.when(reservationRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(reservationRepository).deleteById(1L);

        reservationService.deleteReservation(1L);

        Mockito.verify(reservationRepository).deleteById(1L);
    }
}
