package kino.kinobackend.genre;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import kino.kinobackend.movie.MovieModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GenreModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private int id;

    @Column (name = "genre_name")
    private String name;

    @ManyToMany(mappedBy = "genreList")
    @JsonBackReference
    private Set<MovieModel> movies = new HashSet<>();

}
