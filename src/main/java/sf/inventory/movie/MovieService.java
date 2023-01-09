package sf.inventory.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    public void processItem(String item) {
        Pattern p = Pattern.compile(".*(\\d{4}).*");

        int year;
        try {
            Matcher m = p.matcher(item);
            m.find();
            year = Integer.parseInt(m.group(1));
        } catch (IllegalStateException e) {
            year = -1;
            logger.error(String.format("No year found in item: %s", item));
        }

        Movie movie = new Movie.MovieBuilder(item, year)
                .setUHD(item.toLowerCase().contains("udh") || item.toLowerCase().contains("4k"))
                .setBluray(item.toLowerCase().contains("blu"))
                .setDvd(item.toLowerCase().contains("dvd"))
                .build();
        movieRepository.save(movie);
        logger.info(String.format("Item saved to database: %s", movie));
    }


    public String getMovieCSV() {
        ArrayList<Movie> movieArr = getAllMovies();
        StringBuilder csv = new StringBuilder();
        for (Movie m: movieArr) {
            csv.append(String.format("%s,%s,%s,%s,%s,\n",
                    m.getTitle().replaceAll(",", "\\,"),
                    m.getReleaseYear(),
                    m.getUhd(),
                    m.getBluray(),
                    m.getDvd())
            );
        }
        return csv.toString();
    }


    private ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movieArr = new ArrayList<>();
        for (Movie m : movieRepository.findAll()) {
            movieArr.add(m);
        }
        return movieArr;
    }
}
