package kino.kinobackend.reservation;

import jakarta.persistence.*;
import kino.kinobackend.showing.ShowingModel;
import kino.kinobackend.seat.SeatModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class ReservationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservation_id;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private ShowingModel show;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @ManyToMany
    @JoinTable(
            name = "seat_reservation",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<SeatModel> seatList;


}
