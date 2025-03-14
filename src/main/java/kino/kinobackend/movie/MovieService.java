package kino.kinobackend.movie;

import java.util.List;

public interface MovieService {

    public List<MovieModel> fetchLatestMoviesFromAPI();

    public List<MovieModel> getMovies();

    public List<MovieModel> getUpcomingMovies();

    public MovieModel getMovie(int id);

    public MovieModel createMovie(MovieModel movie);

    public MovieModel updateMovie(MovieModel movie);

    public void deleteMovie(int id);
}
