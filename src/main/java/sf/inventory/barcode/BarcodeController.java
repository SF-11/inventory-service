package sf.inventory.barcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarcodeController {
    @Autowired
    private BarcodeService barcodeService;


    @PostMapping("/barcode")
    public ResponseEntity<String> postBarcode(@RequestBody String barcode) {
       return barcodeService.postBarcode(barcode);
    }
}
