package kino.kinobackend.showing;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import kino.kinobackend.movie.MovieModel;
import kino.kinobackend.reservation.ReservationModel;
import kino.kinobackend.screen.ScreenModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShowingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showing_id")
    private int showingId;

    @Column(name = "start_time")
    private LocalTime startTime;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id", nullable = false) // movie_id er fk
    private MovieModel movieModel;

    @ManyToOne
    @JoinColumn(referencedColumnName = "screen_id")
    @JsonManagedReference
    private ScreenModel screenModel;

    @OneToMany(mappedBy = "showing", cascade = CascadeType.ALL) // reservation er over showing
    @JsonBackReference
    private List<ReservationModel> reservationModel;


}