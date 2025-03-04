package kino.kinobackend.screen;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenService {

    private final ScreenRepository screenRepository;

    public ScreenService(ScreenRepository screenRepository) {
        this.screenRepository = screenRepository;
    }
    public List<ScreenModel> getAllScreens() {
        return screenRepository.findAll();
    }
    public ScreenModel findScreenById(int id) {
        return screenRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("No screen found with id: " + id));
    }
    public ScreenModel createScreen(ScreenModel screen) {
        return screenRepository.save(screen);
    }
    public ScreenModel updateScreen(int id, ScreenModel screen) {
        if(!screenRepository.existsById(screen.getScreenId())) {
            throw new IllegalArgumentException("No screen found with id: " + id);
        }
        return screenRepository.save(screen);
    }
    public void deleteScreen(int id) {
        screenRepository.deleteById(id);
    }

}
