package kino.kinobackend.seat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class SeatServiceImplTest {

    @Mock
    private SeatRepository seatRepository;
    @InjectMocks
    private SeatServiceImpl seatService;

    SeatModel seatModel, seatModel2;

    @BeforeEach
    void setUp() {
        seatModel = new SeatModel();
        seatModel.setSeatId(1);
        seatModel.setSeatRow(10);
        seatModel.setSeatNo(1);

        seatModel2 = new SeatModel();
        seatModel2.setSeatId(2);
        seatModel2.setSeatRow(20);
        seatModel2.setSeatNo(2);
    }

    @Test
    void getSeatTest() {
        Mockito.when(seatRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(seatModel));
        SeatModel seat = seatService.getSeat(2);
        assertEquals(seatModel, seatModel2);
    }

    @Test
    void getAllSeats() {
        List<SeatModel> mockSeats = Arrays.asList(seatModel, seatModel2);
        Mockito.when(seatRepository.findAll()).thenReturn(mockSeats);
        List<SeatModel> seats = seatService.getAllSeats();
        assertEquals(2, seats.size());
        assertEquals(seatModel, seats.get(0));
        assertEquals(seatModel2, seats.get(1));
    }

    @Test
    void updateSeat() {
        Mockito.when(seatRepository.existsById(seatModel.getSeatId())).thenReturn(true);
        Mockito.when(seatRepository.save(Mockito.any(SeatModel.class))).thenReturn(seatModel);

        boolean updated = seatService.updateSeat(seatModel);

        assertTrue(updated);
    }

    @Test
    void seatStatus() {
        Mockito.when(seatRepository.findById(1)).thenReturn(Optional.of(seatModel));

        boolean status = seatService.seatStatus(1);

        assertFalse(status);
    }

    @Test
    void reserveSeat() {
        Mockito.when(seatRepository.findById(1)).thenReturn(Optional.of(seatModel));
        Mockito.when(seatRepository.save(Mockito.any(SeatModel.class))).thenReturn(seatModel);
        boolean reserved = seatService.reserveSeat(1);
        assertTrue(reserved);
    }
}