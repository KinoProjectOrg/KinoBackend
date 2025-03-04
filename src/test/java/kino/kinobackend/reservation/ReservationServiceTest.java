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
        MockitoAnnotations.openMocks(this);
        reservationModel = new ReservationModel();
        reservationModel.setReservationId(1L);
    }

    @Test
    void allReservations() {
        List<ReservationModel> list = new ArrayList<>();
        list.add(reservationModel);
        Mockito.when(reservationRepository.findAll()).thenReturn(list);

        List<ReservationModel> foundReservations = reservationRepository.findAll();

        assertEquals(1, foundReservations.size());
        assertEquals(reservationModel, foundReservations.get(0));
    }

    @Test
    void findReservationById() {
        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservationModel));

        Optional<ReservationModel> foundReservation = reservationRepository.findById(1L);

        assertEquals(Optional.of(reservationModel), foundReservation);
    }

    @Test
    void createReservation() {
        Mockito.when(reservationRepository.save(reservationModel)).thenReturn(reservationModel);

        ReservationModel createdReservation = reservationRepository.save(reservationModel);

        assertEquals(reservationModel, createdReservation);
        Mockito.verify(reservationRepository).save(reservationModel);
    }

    @Test
    void updateReservationTest() {
        Mockito.when(reservationRepository.save(reservationModel)).thenReturn(reservationModel);

        ReservationModel updatedReservation = reservationRepository.save(reservationModel);

        assertEquals(reservationModel, updatedReservation);
        Mockito.verify(reservationRepository).save(reservationModel);
    }


    @Test
    void deleteReservation() {
        Mockito.doNothing().when(reservationRepository).deleteById(1L);

        reservationRepository.deleteById(1L);

        Mockito.verify(reservationRepository).deleteById(1L);
    }
}
