package seat;

import java.util.List;

public interface SeatService {
    List<SeatModel> getAllSeats();
    SeatModel getSeat(int seatNo);
    boolean updateSeat(SeatModel seat);
    boolean seatStatus(int seatNo);
}
