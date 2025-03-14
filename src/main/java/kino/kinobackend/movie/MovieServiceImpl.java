package kino.kinobackend.movie;

import kino.kinobackend.genre.GenreServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDate;
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
    private final GenreServiceImpl genreServiceImpl;

    public MovieServiceImpl(WebClient webClient, MovieRepository movieRepository, GenreServiceImpl genreServiceImpl) {
        this.webClient = webClient;
        this.movieRepository = movieRepository;
        this.genreServiceImpl = genreServiceImpl;
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

    // Retrieve latest movies from tmdb.org and save them to the database including genreNames ...
    @Override
    public List<MovieModel> fetchLatestMoviesFromAPI(){

        int year = LocalDate.now().getYear();

        List<MovieModel> latestMovies = webClient.get()
                .uri("discover/movie?include_adult=false&include_video=false&language=da&page=1&sort_by=popularity.desc&year=" + year )
                .retrieve()
                .bodyToMono(MoviesResponse.class)
                .map(movieResponse -> movieResponse.getResults().stream()  // Stream the results
                        .filter(movie -> movie.getReleaseDate() != null && movie.getReleaseDate().isBefore(LocalDate.now()))
                        .collect(Collectors.toList()))
                .doOnTerminate(() -> System.out.println("Data fetched"))
                .doOnError(error -> System.out.println("Error fetching data: " + error.getMessage()))
                .block();

        for(MovieModel movie : latestMovies) {
            genreServiceImpl.addGenrestoGenreListByMovie(movie);
            movieRepository.save(movie);
        }
        return latestMovies;
    }

    // Get upcoming movies from tmdb.org from today and one page ahead ... returned as a list of MovieModel instances
    @Override
    public List<MovieModel> getUpcomingMovies() {

        List<MovieModel> comingMovies = webClient.get()
                .uri("discover/movie?include_adult=false&include_video=false&language=da&page=1&sort_by=popularity.desc&with_original_language=da&year=2025")
                .retrieve()
                .bodyToMono(MoviesResponse.class)
                .map(movieResponse -> movieResponse.getResults().stream()  // Stream the results
                        .filter(movie -> movie.getReleaseDate() != null && movie.getReleaseDate().isAfter(LocalDate.now()))
                        .collect(Collectors.toList()))
                .doOnTerminate(() -> System.out.println("Data fetched"))
                .doOnError(error -> System.out.println("Error fetching data: " + error.getMessage()))
                .block();

        for(MovieModel movie : comingMovies) {
            genreServiceImpl.addGenrestoGenreListByMovie(movie);
        }
        return comingMovies;
    }

    /*
     *
     * Here we have the methods to fetch and save data from tmdb to our database
     *
     * */

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
