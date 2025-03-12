package kino.kinobackend.showing;

import com.fasterxml.jackson.databind.ObjectMapper;
import kino.kinobackend.customer.CustomerModel;
import kino.kinobackend.employee.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShowingController.class)
@AutoConfigureMockMvc(addFilters = false)
class ShowingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ShowingService showingService;

    private ShowingModel showing;
    @Autowired
    private ShowingRepository showingRepository;

    @BeforeEach
    void setUp() {
        showing = new ShowingModel();
        showing.setShowingId(99);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllShowings() throws Exception {
        List<ShowingModel> showings = new ArrayList<>();
        showings.add(showing);



            Mockito.when(showingService.getAllShowings()).thenReturn(showings);

        mockMvc.perform(get("/showing/showings")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getShowingById() throws Exception {
        ShowingModel showing = new ShowingModel();
        showing.setShowingId(99);


        Mockito.when(showingService.findShowingById(99)).thenReturn(showing);

        mockMvc.perform(get("/showing/showing/99"))
                .andExpect(jsonPath("$.showingId").value(99));
    }

    @Test
    void addShowing() throws Exception {

        Mockito.when(showingService.createShowing(Mockito.any(ShowingModel.class))).thenReturn(showing);

        ObjectMapper mapper = new ObjectMapper();
        String createdShowing = mapper.writeValueAsString(showing);

        mockMvc.perform(post("/showing/newShowing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createdShowing))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.showingId").value(99));
    }

    @Test
    void updateShowing() throws Exception {

        Mockito.when(showingService.updateShowing(Mockito.any(ShowingModel.class))).thenReturn(showing);


        ObjectMapper mapper = new ObjectMapper();
        String updatedShowing = mapper.writeValueAsString(showing);

        mockMvc.perform(put("/showing/updateShowing/99")  //
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedShowing)).andExpect(status().isOk())
                .andExpect(jsonPath("$.showingId").value(99));

    }

    @Test
    void deleteShowing() throws Exception {

        Mockito.doNothing().when(showingService).deleteShowing(Mockito.anyInt());

        mockMvc.perform(delete("/showing/deleteShowing/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}

