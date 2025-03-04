package kino.kinobackend.seat;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService{
    private final SeatRepository seatRepository;

    public SeatServiceImpl(final SeatRepository seatRepository) {this.seatRepository = seatRepository;}

    @Override
    public SeatModel getSeat(int seatId){
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new IllegalArgumentException("Seat not found with id: " + seatId));
    }

    @Override
    public List<SeatModel> getAllSeats(){
        return seatRepository.findAll();
    }

    @Override
    public boolean updateSeat(SeatModel seat) {
        return false;
    }

    @Override
    public boolean seatStatus(int seatId){
        return true;
    }

    public List<SeatModel> getAvailableSeatsForShowing(int showingId){
        List<SeatModel> allSeats = seatRepository.findAll(screenId);
        List<Integer> reservedSeatIds = seatRepository.findReservedSeatsIdsByShowingId(showingId);
        return allSeats.stream()
                .filter(seat -> !reservedSeatIds.contains(seat.getSeatId()))
                .collect(Collectors.toList());
    }
    public boolean reserveSeat(int seatId){
        SeatModel seat = getSeat(seatId);
        if(seat.isReserved()){
            return false;
        }
        seat.setReserved(true);
        seatRepository.save(seat);
        return true;
    }
}
