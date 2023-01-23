package sf.inventory.barcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sf.inventory.external.BarcodeAPI;
import sf.inventory.movie.MovieService;

@Service
public class BarcodeService {

    @Autowired
    private BarcodeAPI barcodeAPI;

    @Autowired
    private MovieService movieService;

    @Autowired
    private static Logger logger = LoggerFactory.getLogger(BarcodeService.class);


    public ResponseEntity<String> postBarcode(String barcode) {
        // validate barcode
        if (!validateBarcode(barcode)) {
            logger.info("Invalid barcode: " + barcode);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid barcode: " + barcode);
        }

        // lookup barcode
        String item = barcodeAPI.lookupBarcode(barcode);
        // TODO check if response is blank - pass on HTTP code from barcode API

        // save to movie repository
        movieService.processItem(item);

        // return response
        return ResponseEntity.ok(item);
    }


    private static boolean validateBarcode(String barcode) {
        return barcode.length() == 12;
    }
}
