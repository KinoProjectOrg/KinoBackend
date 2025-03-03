package seat;


import jakarta.persistence.*;
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
    private int seatId;
    private int seatNo;
    private int seatRow;

    @ManyToOne
    @JoinColumn(name = "ScreenModel", referencedColumnName = "screenId")
    private Screenmodel screenmodel;

    @ManyToMany(mappedBy = "seatList")
    private Set <ReservationModel> reservations = new HashSet<>()

}
