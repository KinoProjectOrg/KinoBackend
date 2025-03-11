package kino.kinobackend.reservation;


import kino.kinobackend.seat.SeatModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@CrossOrigin("*")
public class ReservationRestController {

    private final ReservationService reservationService;

    public ReservationRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/get")
    public List<ReservationModel> getAll(){
        return reservationService.allReservations();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ReservationModel> getById(@PathVariable long id){
         ReservationModel foundReservation = reservationService.findReservationById(id);
         return ResponseEntity.status(HttpStatus.OK).body(foundReservation);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationModel> create(@RequestBody ReservationModel reservationModel){
        ReservationModel createdReservation = reservationService.createReservation(reservationModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReservationModel> update(@PathVariable long id, @RequestBody ReservationModel reservationModel){
        reservationModel.setReservationId(id);
        ReservationModel updatedReservation = reservationService.updateReservation(reservationModel);
        return ResponseEntity.status(HttpStatus.OK).body(updatedReservation);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ReservationModel> deleteReservation(@PathVariable long id){
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{showingId}")
    public ResponseEntity<List<SeatModel>> getByShowingId(@PathVariable int showingId){
        List<SeatModel> reservedSeats = reservationService.findReservedSeatsByShowingId(showingId);
        return ResponseEntity.status(HttpStatus.OK).body(reservedSeats);
    }

    @GetMapping("/seatsInShow/{reservationId}")
    public ResponseEntity<List<SeatModel>> getByReservationId(@PathVariable long reservationId){
        List<SeatModel> foundReservation = reservationService.getSeatsForScreenByReservationId(reservationId);
        return ResponseEntity.status(HttpStatus.OK).body(foundReservation);
    }
}
