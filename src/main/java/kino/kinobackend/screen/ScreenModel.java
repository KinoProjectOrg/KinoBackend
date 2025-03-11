package kino.kinobackend.screen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kino.kinobackend.seat.SeatModel;
import kino.kinobackend.showing.ShowingModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ScreenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screen_id")
    private int screenId;

    @Column(name = "screen_number")
    private int screenNumber;

    @Column(name = "max_rows")
    private int maxRows;

    @Column(name = "seats_per_row")
    private int seatsPerRow;

    @JsonIgnore
    @OneToMany(mappedBy = "screenModel")
    private List<ShowingModel> showings;

    // Add JsonIgnore to break circular reference
    @JsonIgnore
    @OneToMany(mappedBy = "screen")
    private List<SeatModel> seats;

}