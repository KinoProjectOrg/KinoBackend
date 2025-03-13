package kino.kinobackend.movie;

import kino.kinobackend.genre.GenreServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin("*")
public class MovieController {

    /*
    *
    * Initial setup ...
    *
    * */

    private final MovieServiceImpl movieServiceImpl;
    private final GenreServiceImpl genreServiceImpl;

    public MovieController(MovieServiceImpl movieServiceImpl, GenreServiceImpl genreServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
        this.genreServiceImpl = genreServiceImpl;
    }

    /*
    *
    * Add movies to database -- see DataController ...
    *
    * */

    /*
    *
    * Movie stream from tmdb.org no persistence
    *
    * */

    // Fetch upcoming movies and display them as a list of MovieModel ...
    @GetMapping("/upcoming")
    public ResponseEntity<List<MovieModel>> getUpcomingMovies() {

        List<MovieModel> futureMovies = movieServiceImpl.getUpcomingMovies();

        if (futureMovies != null && !futureMovies.isEmpty()) {
            return ResponseEntity.ok(futureMovies);
        }
        else {
            System.out.println("Something is wrong ...");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get")
    public List<MovieModel> getMovies() {
        List<MovieModel> allMoviesInDB = movieServiceImpl.getMovies();

        for (MovieModel movie : allMoviesInDB) {
            genreServiceImpl.addGenrestoGenreListByMovie(movie);
        }

        return allMoviesInDB;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MovieModel> getMovie(@PathVariable int id) {
        MovieModel foundMovie = movieServiceImpl.getMovie(id);
        if (foundMovie != null) {
            genreServiceImpl.addGenrestoGenreListByMovie(foundMovie);
            return ResponseEntity.ok(foundMovie);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<MovieModel> createMovie(@RequestBody MovieModel movieModel) {
        MovieModel createdMovie = movieServiceImpl.createMovie(movieModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    @PutMapping("/filmoperator/update/{movieId}")
    public ResponseEntity<MovieModel> updateMovie(@PathVariable int movieId, @RequestBody MovieModel movieModel) {
        movieModel.setId(movieId);
        MovieModel updatedMovie = movieServiceImpl.updateMovie(movieModel);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/filmoperator/delete/{movieId}")
    public void deleteMovie(@PathVariable int movieId) {
        movieServiceImpl.deleteMovie(movieId);
    }


}
