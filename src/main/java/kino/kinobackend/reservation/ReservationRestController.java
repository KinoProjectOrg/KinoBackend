package kino.kinobackend.reservation;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
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

    @PostMapping("/create")
    public ResponseEntity<ReservationModel> create(@RequestBody ReservationModel reservationModel){
        ReservationModel createdReservation = reservationService.createReservation(reservationModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReservationModel> update(@PathVariable long id, @RequestBody ReservationModel reservationModel){
        reservationModel.setReservation_id(id);
        ReservationModel updatedReservation = reservationService.updateReservation(reservationModel);
        return ResponseEntity.status(HttpStatus.OK).body(updatedReservation);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReservation(@PathVariable long id){
        reservationService.deleteReservation(id);
    }
}
