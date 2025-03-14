package kino.kinobackend.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class MovieControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ObjectMapper objectMapper;


    private MovieModel movie1;
    private MovieModel movie2;



    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();

        movie1 = new MovieModel();
        movie1.setId(1);
        movie1.setTitle("Movie1");
        movie1 = movieRepository.save(movie1);

        movie2 = new MovieModel();
        movie2.setId(2);
        movie2.setTitle("Movie2");
        movie2 = movieRepository.save(movie2);
    }

    @Test
    void getUpcomingMoviesIntegrationTest() throws Exception {
        List<MovieModel> futureMoviesTest = movieRepository.findAll();
        mockMvc.perform(get("/movie/upcoming")
        .contentType(MediaType.APPLICATION_JSON)
                        .content(futureMoviesTest.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getMoviesIntegrationTest()  throws Exception {
        mockMvc.perform(get("/movies/get")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(movieRepository.count()))
                .andExpect(jsonPath("$[0].title").value(movie1.getTitle()))
                .andExpect(jsonPath("$[1].title").value(movie2.getTitle()));
    }

    @Test
    void getMovieIntegrationTest() throws Exception {
        mockMvc.perform(get("/movies/get/" + movie1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(movie1.getId()));
    }

    @Test
    void createMovieIntegrationTest() throws Exception {
        MovieModel newMovie = new MovieModel();
        newMovie.setTitle("Lord of the Rings");

        mockMvc.perform(post("/movies/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newMovie)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(newMovie.getTitle()));

        assertEquals(3, movieRepository.count());
    }

    @Test
    void updateMovieIntegrationTest() throws Exception {
        MovieModel movieUpdate = new MovieModel();
        movieUpdate.setId(movie1.getId());
        movieUpdate.setTitle("Spiderman");

        mockMvc.perform(put("/movies/filmoperator/update/" + movieUpdate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Spiderman"));
        assertEquals(2, movieRepository.count());
    }

    @Test
    void deleteMovieIntegrationTest() throws Exception {
        mockMvc.perform(delete("/movies/filmoperator/delete/" + movie1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(1, movieRepository.count());
        assertFalse(movieRepository.existsById(movie1.getId()));
    }
}