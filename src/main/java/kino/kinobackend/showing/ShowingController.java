package kino.kinobackend.showing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/showing")
public class ShowingController {

    private final ShowingService showingService;

    public ShowingController(ShowingService showingService) {
        this.showingService = showingService;
    }

    @GetMapping("/showings")
    public List<ShowingModel> getAllShowings() {
        return showingService.getAllShowings();
    }

    @GetMapping("/showing/{id}")
    public ResponseEntity<ShowingModel> getShowingById(@PathVariable int id) {
        ShowingModel foundShowing = showingService.findShowingById(id);
        if (foundShowing == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(foundShowing);
        }
    }

    @PostMapping(("/newShowing"))
    public ResponseEntity<ShowingModel> addShowing(@RequestBody ShowingModel showingModel) {
        if (showingService.getAllShowings().contains(showingModel)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            showingService.createShowing(showingModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(showingModel);
        }
    }

        @PutMapping("/updateShowing/{id}")
        public ResponseEntity<ShowingModel> updateShowing ( @PathVariable int id, @RequestBody ShowingModel showingModel) {
            showingModel.setShowingId(id);
            ShowingModel updatedShowing = showingService.updateShowing(showingModel);
            return ResponseEntity.ok(updatedShowing);

        }

    @DeleteMapping("/deleteShowing/{id}")
    public ResponseEntity<Void> deleteShowing(@PathVariable int id) {
        showingService.deleteShowing(id);
        return ResponseEntity.noContent().build();
    }
}
