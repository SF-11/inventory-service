package sf.inventory.barcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sf.inventory.external.BarcodeAPI;

@Service
public class BarcodeService {

    @Autowired
    private BarcodeAPI barcodeApi;

    public ResponseEntity<String> postBarcode(String barcode) {
        // validate barcode
        if (!validateBarcode(barcode)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid barcode: " + barcode);
        }

        // lookup barcode


        // post result to sheets


        // return response
        return ResponseEntity.ok(barcode);
    }



    private static boolean validateBarcode(String barcode) {
        return barcode.length() == 12;
    }
}
