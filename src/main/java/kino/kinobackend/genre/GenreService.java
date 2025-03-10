package kino.kinobackend.genre;

import kino.kinobackend.movie.MovieModel;

import java.util.List;

public interface GenreService {

    public List<GenreModel> fetchAllGenres();

    public void addGenrestoGenreListByMovie(MovieModel movie);

}
