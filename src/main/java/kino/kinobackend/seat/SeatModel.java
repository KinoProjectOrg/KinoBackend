package kino.kinobackend.seat;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import kino.kinobackend.reservation.ReservationModel;
import kino.kinobackend.screen.ScreenModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SeatModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private int seatId;

    @Column(name = "seat_no")
    private int seatNo;

    @Column(name = "seat_row")
    private int seatRow;

    private boolean isReserved;

    @ManyToOne
    @JoinColumn(name = "screen_id", referencedColumnName = "screen_id")
    private ScreenModel screenId;

    @ManyToMany(mappedBy = "seatList")
    @JsonIgnoreProperties("seatList")
    private Set<ReservationModel> reservations = new HashSet<>();

}
