package kino.kinobackend.configuration;

import kino.kinobackend.genre.GenreModel;
import kino.kinobackend.genre.GenreRepository;
import kino.kinobackend.genre.GenreServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    public DataController(GenreServiceImpl genreServiceImpl, GenreRepository genreRepository) {
        this.genreServiceImpl = genreServiceImpl;
        this.genreRepository = genreRepository;
    }

    /*
    *
    * Fetching tools below
    *
    * */


    // Genres fetching tool ...
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
