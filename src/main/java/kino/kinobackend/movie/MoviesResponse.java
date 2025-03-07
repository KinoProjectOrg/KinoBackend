package kino.kinobackend.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class MoviesResponse {

    // Map the results field from the api response to a list of MovieModel objects ...
    @JsonProperty("results")
    private List<MovieModel> results;

}