package kino.kinobackend.movie;

import java.util.List;

public interface MovieService {

    public List<MovieModel> getMovies();

    public String getUpcomingMovies();

    public List<MovieModel> getFutureMovies();

    public MovieModel getMovie(int id);

}
