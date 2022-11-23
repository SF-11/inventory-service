package sf.inventory.external;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BarcodeAPI {


    public Map<String, String> lookupBarcode(String barcode) {
        String response = "";

        BasicJsonParser parser = new BasicJsonParser();
        parser.parseMap(response);
        return null;
    }

}
