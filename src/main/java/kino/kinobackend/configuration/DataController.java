package kino.kinobackend.configuration;

import kino.kinobackend.genre.GenreModel;
import kino.kinobackend.genre.GenreRepository;
import kino.kinobackend.genre.GenreServiceImpl;
import kino.kinobackend.movie.MovieModel;
import kino.kinobackend.movie.MovieServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tool/fetch/")
public class DataController {

    /*
    *
    * Init setup with constructor injection
    *
    * */

    private final GenreServiceImpl genreServiceImpl;
    private final GenreRepository genreRepository;
    private final MovieServiceImpl movieServiceImpl;

    public DataController(GenreServiceImpl genreServiceImpl, GenreRepository genreRepository, MovieServiceImpl movieServiceImpl) {
        this.genreServiceImpl = genreServiceImpl;
        this.genreRepository = genreRepository;
        this.movieServiceImpl = movieServiceImpl;
    }

    /*
    *
    * Fetching tools below
    *
    * */

    @GetMapping("/movies")
    public ResponseEntity<List<MovieModel>> fetchLatestMoviesWithGenres() {

        // Fetch movies, add genreNames to each and save them to the local database ...
        List<MovieModel> movieModels = movieServiceImpl.fetchLatestMoviesFromAPI();

        if(movieModels != null) {
            return ResponseEntity.ok(movieModels);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/genres")
    public ResponseEntity<List<GenreModel>> fetchAllGenres() {
        List<GenreModel> movieGenres = genreServiceImpl.fetchAllGenres();
        if (movieGenres != null && !movieGenres.isEmpty()) {
            movieGenres.forEach(g -> genreRepository.save(g));
            return ResponseEntity.ok(movieGenres);
        }
        else {
            System.out.println("Something is wrong ...");
            return ResponseEntity.notFound().build();
        }
    }
}
