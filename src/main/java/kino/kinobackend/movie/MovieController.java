package kino.kinobackend.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
