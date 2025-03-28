package kino.kinobackend.genre;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreModel, Integer> {
    GenreModel getGenreModelById(int id);
}
