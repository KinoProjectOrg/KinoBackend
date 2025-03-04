package kino.kinobackend.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private int id; // Is set from external api ( themoviedb.org ) ...
    private String title;

    @JsonProperty("genre_ids")
    private List<Integer> genreIds;

    private int minAge;
    private int runtime;
    private LocalDate startDate;
    private LocalDate endDate;
    private String overview;
    @JsonProperty("poster_path") // This is needed to get the string  returned and reckoned as it is named poster_path in Json and if not returns null ...
    private String posterPath;

    @JsonProperty("release_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private boolean status;
}