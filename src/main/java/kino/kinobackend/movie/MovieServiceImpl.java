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
        return movieRepository.findAll();
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

        return webClient.get()
                .uri("movie?include_adult=false&include_video=false&language=da&page=1&sort_by=popularity.desc&with_original_language=da&year=2025")
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
        return movieRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }

    @Override
    public MovieModel createMovie(MovieModel movie) {
        return movieRepository.save(movie);
    }

    @Override
    public MovieModel updateMovie(MovieModel movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(int id) {
        movieRepository.deleteById(id);
    }
}
