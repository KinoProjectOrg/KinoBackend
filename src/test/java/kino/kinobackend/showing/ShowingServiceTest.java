package kino.kinobackend.showing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ShowingServiceTest {

    @Mock
    ShowingRepository showingRepository;

    @InjectMocks
    ShowingService showingService;
    ShowingModel showingModel;

    @BeforeEach
    void setUp() {
        showingModel = new ShowingModel();
        showingModel.setShowingId(99);
    }

    @Test
    void getAllShowings() {
        List<ShowingModel> showings = showingService.getAllShowings();
        showings.add(showingModel);

        Mockito.when(showingRepository.findAll()).thenReturn(showings);

        List<ShowingModel> showingModels = showingService.getAllShowings();

        assertEquals(showings, showingModels);
        assertEquals(showingModel, showingModels.get(0));
    }

    @Test
    void findShowingByShowingId() {

        Mockito.when(showingRepository.findById(99)).thenReturn(Optional.of(showingModel));

        ShowingModel showingModel1 = showingService.findShowingById(99);

        assertEquals(showingModel, showingModel1);
    }
    @Test
    void createShowing() {
        Mockito.when(showingRepository.save(showingModel)).thenReturn(showingModel);

        ShowingModel showingModel1 = showingService.createShowing(showingModel);

        assertEquals(showingModel, showingModel1);
        assertEquals(showingModel.getShowingId(), showingModel1.getShowingId());
    }
    @Test
    void updateShowing() {
        Mockito.when(showingRepository.existsById(showingModel.getShowingId())).thenReturn(true);
        Mockito.when(showingRepository.save(showingModel)).thenReturn(showingModel);

        ShowingModel showingModel1 = showingService.updateShowing(showingModel);

        assertEquals(showingModel, showingModel1);
    }
    @Test
    void deleteShowing() {
        Mockito.doNothing().when(showingRepository).deleteById(showingModel.getShowingId());

        showingService.deleteShowing(showingModel.getShowingId());
        Mockito.verify(showingRepository, Mockito.times(1)).deleteById(showingModel.getShowingId());
    }
}