package kino.kinobackend.movie;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/movies")
public class MovieController {

    /*
    *
    * Initial setup ...
    *
    * */

    private final MovieServiceImpl movieServiceImpl;

    public MovieController(MovieServiceImpl movieServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
    }

    /*
    *
    * Add movies to database
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
        return movieServiceImpl.getMovies();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MovieModel> getMovie(@PathVariable int id) {
        MovieModel foundMovie = movieServiceImpl.getMovie(id);
        return ResponseEntity.ok(foundMovie);
    }

    @PostMapping("/create")
    public ResponseEntity<MovieModel> createMovie(@RequestBody MovieModel movieModel) {
        MovieModel createdMovie = movieServiceImpl.createMovie(movieModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    @PutMapping("/update")
    public ResponseEntity<MovieModel> updateMovie(@RequestBody MovieModel movieModel) {
        MovieModel updatedMovie = movieServiceImpl.updateMovie(movieModel);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/delete")
    public void deleteMovie(int id) {
        movieServiceImpl.deleteMovie(id);
    }
}

  /*  @GetMapping("/genre")*/

