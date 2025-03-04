package kino.kinobackend.showing;

import jakarta.persistence.*;
import kino.kinobackend.movie.MovieModel;
import kino.kinobackend.screen.ScreenModel;

import java.time.LocalTime;

@Entity
public class ShowingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int showingId;
    private LocalTime start_time;
    private LocalTime end_time;

//    @ManyToOne
//    @JoinColumn(referencedColumnName = "movieId", nullable = false)
//    private MovieModel movieModel;
    @ManyToOne
    @JoinColumn(referencedColumnName = "screenId")
    private ScreenModel screenModel;


    public int getShowingId() {
        return showingId;
    }

    public void setShowingId(int showingId) {
        this.showingId = showingId;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public LocalTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalTime end_time) {
        this.end_time = end_time;
    }

//    public MovieModel getMovie() {
//        return movieModel;
//    }

//    public void setMovie(MovieModel movieModel) {
//        this.movieModel = movieModel;
//    }

    public ScreenModel getScreen() {
        return screenModel;
    }

    public void setScreen(ScreenModel screenModel) {
        this.screenModel = screenModel;
    }
}
