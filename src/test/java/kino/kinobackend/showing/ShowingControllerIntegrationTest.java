package kino.kinobackend.showing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import kino.kinobackend.KinoBackendApplication;
import kino.kinobackend.movie.MovieModel;
import kino.kinobackend.movie.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class ShowingControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ShowingRepository showingRepository;

    @Autowired
    private MovieRepository movieRepository;

    ShowingModel showingModel = new ShowingModel();
    MovieModel movieModel = new MovieModel();
    @BeforeEach
    void setUp() {
        movieRepository.saveAndFlush(movieModel);
        showingRepository.deleteAll();
        showingModel.setMovieModel(movieModel);
        showingModel.setStartTime(LocalTime.of(10,0));
        showingRepository.saveAndFlush(showingModel);



    }

    @Test
    void getAllShowings() throws Exception {
        mockMvc.perform(get("/showing/showings").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

    }

    @Test
    void getShowingById() throws Exception {
        mockMvc.perform(get("/showing/showing/" + showingModel.getShowingId())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.showingId").value(showingModel.getShowingId()));
    }

    @Test
    void addShowing() throws Exception {
        ShowingModel showingModel = new ShowingModel();
        showingModel.setStartTime(LocalTime.of(20,13));
        showingModel.setMovieModel(movieModel);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String newShowing = mapper.writeValueAsString(showingModel);
        mockMvc.perform(post("/showing/newShowing").contentType(MediaType.APPLICATION_JSON).content(newShowing)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.startTime").value(showingModel.getStartTime() + ":00"));
    }

    @Test
    void updateShowing() throws Exception {
        ShowingModel updatedShowingModel = new ShowingModel();
        updatedShowingModel.setShowingId(showingModel.getShowingId());
        updatedShowingModel.setStartTime(LocalTime.of(20,13));
        updatedShowingModel.setMovieModel(movieModel);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String newShowing = mapper.writeValueAsString(showingModel);

        mockMvc.perform(put("/showing/updateShowing/" + showingModel.getShowingId())
                .contentType(MediaType.APPLICATION_JSON).content(newShowing)).andExpect(status().isOk())
                .andExpect(jsonPath("$.startTime").value(showingModel.getStartTime() + ":00"));

    }

    @Test
    void deleteShowing() throws Exception {
        mockMvc.perform(delete("/showing/deleteShowing/" + showingModel.getShowingId())).andExpect(status().isNoContent());
    }

}