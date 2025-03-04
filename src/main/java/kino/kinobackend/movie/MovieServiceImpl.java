package kino.kinobackend.movie;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    /*
    *
    * Initial setup with constructor injection
    *
    * */

    private final MovieRepository movieRepository;
    private final WebClient webClient;

    public MovieServiceImpl(WebClient webClient, MovieRepository movieRepository) {
        this.webClient = webClient;
        this.movieRepository = movieRepository;
    }

    /*
    *
    * Here we have the methods to fetch and save data from tmdb to our database
    *
    * */

    @Override
    public MovieModel fetchMovie(String title) {
        return null;
    }


    /*
    *
    * Here we have methods that we can use, when we have retrieved data and saved them in our database
    *
    * */


    @Override
    public List<MovieModel> getMovies() {
        // repo ...
        List<MovieModel> movies = new ArrayList<>();
        return movies;
    }

    /*
    *
    * Here are methods which just fetches "live data" form tmdb api without saving them
    *
    * */

    // Retrieve a movie from tmdb.org and save it to the database ...


    // Get upcoming movies from tmdb.org from today and one page ahead ... returned as a list of MovieModel instances
    @Override
    public List<MovieModel> getUpcomingMovies() {

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
