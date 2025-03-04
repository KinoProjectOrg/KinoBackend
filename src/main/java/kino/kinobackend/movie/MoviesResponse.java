package kino.kinobackend.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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