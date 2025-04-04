package kino.kinobackend.showing;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JoinColumn(referencedColumnName = "screen_id") // fk
    @JsonIgnoreProperties({"showings", "seats"})
    private ScreenModel screenModel;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id", nullable = false) // movie_id er fk
    private MovieModel movieModel;

    @OneToMany(mappedBy = "showing", cascade = CascadeType.ALL) // reservation er over showing
    @JsonIgnore
    private List<ReservationModel> reservationModel;

}