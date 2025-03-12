package kino.kinobackend.screen;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ScreenController {

    private final ScreenService screenService;

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @GetMapping("/screens")
    public List<ScreenModel> screens() {
        return screenService.getAllScreens();
    }
    @GetMapping("/screen/{id}")
    public ResponseEntity<ScreenModel> getScreenById(@PathVariable int id) {
        ScreenModel foundScreen = screenService.findScreenById(id);
        if (foundScreen == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(foundScreen);
        }
    }
    @PostMapping("/newScreen")
    public ResponseEntity<ScreenModel> newScreen(@RequestBody ScreenModel screenModel) {
        if (screenService.getAllScreens().contains(screenModel)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            screenService.createScreen(screenModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(screenModel);
        }
    }
    @PutMapping("/updateScreen/{id}")
    public ResponseEntity<ScreenModel> updateScreen(@PathVariable int id, @RequestBody ScreenModel screenModel) {
        screenModel.setScreenId(id);
        screenService.updateScreen(id, screenModel);
            return ResponseEntity.status(HttpStatus.OK).body(screenModel);
    }
    @DeleteMapping("/deleteScreen/{id}")
    public void deleteScreen(@PathVariable int id) {
        screenService.deleteScreen(id);
    }
}
