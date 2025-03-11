package kino.kinobackend.genre;

import kino.kinobackend.movie.MovieModel;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    /*
     *
     * Initial setup with constructor injection
     *
     * */

    private final GenreRepository genreRepository;
    private final WebClient webClient;

    public GenreServiceImpl(WebClient webClient, GenreRepository genreRepository) {
        this.webClient = webClient;
        this.genreRepository = genreRepository;
    }

    // Get all genres from tmdb.org ...
    @Override
    public List<GenreModel> fetchAllGenres() {
        return webClient.get()
                .uri("genre/movie/list?language=en")
                .retrieve()
                .bodyToMono(GenreResponse.class) // Deserialize the response into JsonResponse object
                .map(GenreResponse::getGenres) // get the genres ...
                .block(); // Blocking for the result to be returned synchronously
    }

    // Get and add genres for a specific movie from the local database by supplied ids (genreIds)...
    public void addGenrestoGenreListByMovie(MovieModel movie) {

        // Get get specific movie's list of genreIds ...
        List<Integer> ids = movie.getGenreNames();
        // get genres directly from external api ...
        List<GenreModel> genres = fetchAllGenres();
//        // Alternative: get genres from local database ...
//        List<GenreModel> genres = new ArrayList<>();
//        genres.addAll(genreRepository.findAll());

        for(int genreId : ids) {
            for(GenreModel genre : genres) {
                if(genre.getId() == genreId) {
                    movie.getGenreNames().add(genre.getId());
                }
            }
        }
    }
}
