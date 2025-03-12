package kino.kinobackend.showing;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowingService{
    private final ShowingRepository showingRepository;

    public ShowingService(ShowingRepository showingRepository) {
        this.showingRepository = showingRepository;
    }

    public List<ShowingModel> getAllShowings() {
        return showingRepository.findAll();
    }

    public ShowingModel findShowingById(int id) {
        return showingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Show not found with id: " + id + " was not found"));
    }

    public ShowingModel createShowing(ShowingModel showingModel) {
        return showingRepository.save(showingModel);
    }

    public ShowingModel updateShowing(ShowingModel showingModel) {
        if(!showingRepository.existsById(showingModel.getShowingId())) {
            throw new IllegalArgumentException("Showing with id " + showingModel.getShowingId() + " not found");
        }
        return showingRepository.save(showingModel);
    }

    public void deleteShowing(int id) {
        showingRepository.deleteById(id);
    }
}

