package kino.kinobackend.reservation;


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
    public ReservationModel getById(@PathVariable long id){
        return reservationService.findReservationById(id);
    }

    @PostMapping("/create")
    public ReservationModel create(@RequestBody ReservationModel reservationModel){
        return reservationService.createReservation(reservationModel);
    }

    @PutMapping("/update/{id}")
    public ReservationModel update(@PathVariable long id, @RequestBody ReservationModel reservationModel){
        reservationModel.setReservation_id(id);
        return reservationService.updateReservation(reservationModel);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id){
        reservationService.deleteReservation(id);
    }
}
