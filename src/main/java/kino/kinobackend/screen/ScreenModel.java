package kino.kinobackend.screen;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import kino.kinobackend.seat.SeatModel;
import kino.kinobackend.showing.ShowingModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "screenModel", cascade = CascadeType.ALL) // mappedBy betyder at showing ejer relationen
    @JsonBackReference
    private List<ShowingModel> showingModel = new ArrayList<>();

    @OneToMany(mappedBy = "screenId", cascade = CascadeType.ALL) // seatModel ejer relationen
    @JsonBackReference
    private List<SeatModel> seatModel = new ArrayList<>();


}