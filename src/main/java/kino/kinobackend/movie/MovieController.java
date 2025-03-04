package kino.kinobackend.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieServiceImpl movieServiceImpl;

    public MovieController(MovieServiceImpl movieServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
    }

    // Fetch upcoming movies directly from  TMDB ...
    @GetMapping("/coming")
    public ResponseEntity<String> getUpcomingMovies() {

        String futureMovies = movieServiceImpl.getUpcomingMovies();

        if (futureMovies != null) {
            return ResponseEntity.ok(futureMovies);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


    // Fetch upcoming movies and display them as a list of MovieModel ...
    @GetMapping("/upcoming")
    public ResponseEntity<List<MovieModel>> getFutureMovies() {

        List<MovieModel> futureMovies = movieServiceImpl.getFutureMovies();

        if (futureMovies != null && !futureMovies.isEmpty()) {
            return ResponseEntity.ok(futureMovies);
        }
        else {
            System.out.println("Something is wrong ...");
            return ResponseEntity.notFound().build();
        }
    }
}
