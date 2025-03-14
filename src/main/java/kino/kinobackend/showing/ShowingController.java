package kino.kinobackend.showing;

import kino.kinobackend.movie.MovieModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/showing")
@CrossOrigin("*")
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
        try {
            // Now save the showing
            ShowingModel savedShowing = showingService.createShowing(showingModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedShowing);
        } catch (Exception e) {
            e.printStackTrace();  // Log the error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

        @PutMapping("/updateShowing/{id}")
        public ResponseEntity<ShowingModel> updateShowing ( @PathVariable int id, @RequestBody ShowingModel showingModel) {
            showingModel.setShowingId(id);
            ShowingModel updatedShowing = showingService.updateShowing(showingModel);
            return ResponseEntity.status(HttpStatus.OK).body(updatedShowing);

        }

    @DeleteMapping("/deleteShowing/{id}")
    public ResponseEntity<Void> deleteShowing(@PathVariable int id) {
        showingService.deleteShowing(id);
        return ResponseEntity.noContent().build();
    }

        @PostMapping("/filmoperator/create")
        public ResponseEntity<ShowingModel> createShowingOp(@RequestBody ShowingModel show) {
            ShowingModel showingModel = showingService.createShowing(show);
            return ResponseEntity.ok(showingModel);
        }

        @DeleteMapping("/filmoperator/delete/{showingId}")
        public void deleteShowingOp(@PathVariable int showingId) {
        showingService.deleteShowing(showingId);
        }
    }


