package seat;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService{
    private final SeatRepository seatRepository;

    public SeatServiceImpl(final SeatRepository seatRepository) {this.seatRepository = seatRepository;}

    @Override
    public SeatModel getSeat(int seatNo){

    }

    @Override
    public List<SeatModel> getAllSeats(){
        return
    }

    @Override
    public boolean updateSeat(SeatModel seat) {
        return false;
    }

    @Override
    public boolean seatStatus(int seatNo){
        return true;
    }
}
