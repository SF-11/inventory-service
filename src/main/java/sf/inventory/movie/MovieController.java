package sf.inventory.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class MovieController {


        @Autowired
        private MovieService movieService;


        @GetMapping("/movies")
        public ResponseEntity<String> getMovies() {;
            return ResponseEntity.ok(movieService.getMovieCSV());
        }
}
