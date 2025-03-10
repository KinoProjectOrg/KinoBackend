package kino.kinobackend.movie;

import com.fasterxml.jackson.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import kino.kinobackend.genre.GenreModel;
import kino.kinobackend.showing.ShowingModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="movie_id")
    private int movieId; // Is set from external api ( themoviedb.org ) ...
    private String title;

    @JsonProperty("genre_ids")
    private String genreIds;

    @Column(name="min_age")
    private int minAge;

    private int runtime;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;

    private String overview;
    
    @JsonProperty("poster_path") // This is needed to get the string  returned and reckoned as it is named poster_path in Json and if not returns null ...
    private String posterPath;

    @JsonProperty("release_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private boolean status;

    @OneToMany(mappedBy = "movieModel", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ShowingModel> showingModels = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonIgnore
    private List<GenreModel> genreList;

    // Overriding the setter for posterPath
    public void setPosterPath(String path) {
        this.posterPath = "https://image.tmdb.org/t/p/w500" + path;  // Full path to movie poster img - change the w500 for other sizes...
    }
}