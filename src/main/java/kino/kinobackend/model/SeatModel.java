package kino.kinobackend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Screen screen;
}
