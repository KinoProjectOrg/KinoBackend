package kino.kinobackend.showing;

import kino.kinobackend.movie.MovieModel;
import kino.kinobackend.movie.MovieRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ShowingService{
    private final ShowingRepository showingRepository;

    private final MovieRepository movieRepository;

    public ShowingService(ShowingRepository showingRepository, MovieRepository movieRepository) {
        this.showingRepository = showingRepository;
        this.movieRepository = movieRepository;
    }

    public List<ShowingModel> getAllShowings() {
        return showingRepository.findAll();
    }

    public ShowingModel findShowingById(int id) {
        return showingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Show not found with id: " + id + " was not found"));
    }

    public ShowingModel createShowing(ShowingModel showingModel) {
        return showingRepository.save(showingModel);
    }

    public ShowingModel updateShowing(ShowingModel showingModel) {
        if(!showingRepository.existsById(showingModel.getShowingId())) {
            throw new IllegalArgumentException("Showing with id " + showingModel.getShowingId() + " not found");
        }
        return showingRepository.save(showingModel);
    }

    public MovieModel findMovieId(int movieId){
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found with id: " + movieId + " was not found"));
    }

    public void deleteShowing(int id) {
        showingRepository.deleteById(id);
    }
}

