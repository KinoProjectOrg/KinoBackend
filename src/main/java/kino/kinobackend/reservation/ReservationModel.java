package kino.kinobackend.reservation;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import kino.kinobackend.customer.CustomerModel;
import kino.kinobackend.seat.SeatModel;
import kino.kinobackend.showing.ShowingModel;
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
    @Column(name = "reservation_id")
    private long reservationId;

    @ManyToOne
    @JoinColumn(name = "showing_id")
    @JsonManagedReference
    private ShowingModel showing;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    private CustomerModel customer;


    @ManyToMany
    @JoinTable(
            name = "seat_reservation",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    @JsonManagedReference
    private List<SeatModel> seatList;
}
