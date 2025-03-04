package kino.kinobackend.movie;

import java.util.List;

public interface MovieService {

    public MovieModel fetchMovie(String title);

    public List<MovieModel> getMovies();

    public List<MovieModel> getUpcomingMovies();

    public MovieModel getMovie(int id);

}
