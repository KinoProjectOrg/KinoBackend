package kino.kinobackend.seat;

import java.util.List;

public interface SeatService {
    List<SeatModel> getAllSeats();
    SeatModel getSeat(int seatId);
    boolean updateSeat(SeatModel seat);
    boolean seatStatus(int seatId);
    List<SeatModel> getAvailableSeatsForShowing(int showingId, int screenId);
    boolean reserveSeat(int seatId);
}
