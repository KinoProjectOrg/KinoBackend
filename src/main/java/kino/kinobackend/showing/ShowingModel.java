package kino.kinobackend.showing;

import jakarta.persistence.*;
import kino.kinobackend.screen.ScreenModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

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

//    Unødvendig??
    @Column(name="end_time")
    private LocalTime endTime;

    //    @ManyToOne
//    @JoinColumn(referencedColumnName = "movieId", nullable = false)
//    private MovieModel movieModel;

    @ManyToOne
    @JoinColumn(referencedColumnName = "screen_id")
    private ScreenModel screenModel;


}