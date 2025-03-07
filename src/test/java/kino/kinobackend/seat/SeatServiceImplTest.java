package kino.kinobackend.seat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class SeatServiceImplTest {

    @Mock
    private SeatRepository seatRepository;
    @InjectMocks
    private SeatServiceImpl seatService;

    SeatModel seatModel;

    @BeforeEach
    void setUp() {
        seatModel = new SeatModel();
        seatModel.setSeatId(1);
    }

    @Test
    void getSeatTest() {
        Mockito.when(seatRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(seatModel));
        SeatModel seat = seatService.getSeat(2);
        assertEquals(seatModel, seat);
    }

    @Test
    void getAllSeats() {
        Mockito.when(seatRepository.findAll()).thenReturn(List<SeatModel> )
    }

    @Test
    void updateSeat() {
    }

    @Test
    void seatStatus() {
    }

    @Test
    void reserveSeat() {
    }
}