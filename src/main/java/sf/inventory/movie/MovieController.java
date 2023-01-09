package sf.inventory.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

        @Autowired
        private MovieRepository movieRepository;

        @Autowired
        private MovieService movieService;


        @GetMapping("/movies")
        public ResponseEntity<String> getMovies() {

            movieService.getMovieCSV();

            for (Movie m : movieRepository.findAll()) {
                System.out.println(m);
            }
            return null;
        }
}
