package seat;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kino.kinobackend.screen.ScreenModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "ScreenModel", referencedColumnName = "screen_id")
    private Screenmodel screenmodel;

    @ManyToMany(mappedBy = "seatList")
    private Set <ReservationModel> reservations = new HashSet<>()

}
