package kino.kinobackend.movie;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private WebClient webClient;

    public MovieServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }


    @Override
    public List<MovieModel> getMovies() {
        // repo ...
        List<MovieModel> movies = new ArrayList<>();
        return movies;
    }

    @Override
    public String getUpcomingMovies(){

        return webClient.get()
                .uri("/upcoming")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public List<MovieModel> getFutureMovies() {

        LocalDate today = LocalDate.now();

        return webClient.get()
                .uri("/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&primary_release_date.gte=" + today + "&sort_by=primary_release_date.asc")
                                // .queryParam("release_date.gte", today)
                                //.build()
                .retrieve()
                .bodyToMono(MoviesResponse.class)
                .map(movieResponse -> movieResponse.getResults().stream()  // Stream the results
                        .filter(movie -> movie.getReleaseDate() != null && movie.getReleaseDate().isAfter(LocalDate.now()))
                        .collect(Collectors.toList()))
                .doOnTerminate(() -> System.out.println("Data fetched"))
                .doOnError(error -> System.out.println("Error fetching data: " + error.getMessage()))
                .block();
    }

    @Override
    public MovieModel getMovie(int id) {

        MovieModel movie = new MovieModel();
        return movie;
    }
}
