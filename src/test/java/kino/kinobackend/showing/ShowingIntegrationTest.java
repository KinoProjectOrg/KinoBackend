package kino.kinobackend.showing;

import kino.kinobackend.KinoBackendApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
                classes = KinoBackendApplication.class)
@AutoConfigureMockMvc
public class ShowingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShowingService showingService;

    @Autowired
    private ShowingRepository showingRepository;

    @Autowired
    private ShowingController showingController;

    @BeforeEach
    void setUp(){
        ShowingModel showingModel = new ShowingModel();
        showingModel.setShowingId(99);

        showingRepository.save(showingModel);
    }


}
