package kino.kinobackend.genre;

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

    // Get all genres ...
    @Override
    public List<GenreModel> fetchAllGenres() {
        return webClient.get()
                .uri("genre/movie/list?language=en")
                .retrieve()
                .bodyToMono(GenreResponse.class) // Deserialize the response into JsonResponse object
                .map(GenreResponse::getGenres) // get the genres ...
                .block(); // Blocking for the result to be returned synchronously
    }

}
