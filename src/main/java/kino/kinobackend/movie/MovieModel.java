package kino.kinobackend.movie;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import kino.kinobackend.showing.ShowingModel;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MovieModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;
    private String title;
    private String genre;
    private int min_age;
    private LocalDate start_date;
    private LocalDate end_date;
    private String plot;
    private boolean isActive;

    @OneToMany(mappedBy = "movie")
    @JsonBackReference
    private Set<ShowingModel> shows = new HashSet<ShowingModel>();

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getMin_age() {
        return min_age;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
