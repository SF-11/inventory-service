package sf.inventory.barcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sf.inventory.external.BarcodeAPI;
import sf.inventory.external.exception.BarcodeResponseException;
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
            logger.error("Invalid barcode: " + barcode);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid barcode: " + barcode);
        }
        // don't lookup a barcode we already have
        if (movieService.recordExists(barcode)) {
            logger.error("Barcode already exists in database: " + barcode);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Barcode already exists in database: " + barcode);
        }

        // lookup barcode
        String item = "";
        try {
            item = barcodeAPI.lookupBarcode(barcode);
        } catch (BarcodeResponseException e) {
            return ResponseEntity.status(e.getResponseCode()).body(e.getMessage());
        }
        // TODO check if response is blank - pass on HTTP code from barcode API

        // save to movie repository
        movieService.processItem(barcode, item);

        // return response
        return ResponseEntity.ok(item);
    }


    private static boolean validateBarcode(String barcode) {
        return barcode.length() == 12;
    }
}
