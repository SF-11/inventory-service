package sf.inventory.external;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sf.inventory.external.exception.BarcodeResponseException;

import java.io.IOException;

@Component
public class BarcodeAPI {

    @Value("${barcode.api.url}")
    private String url;

    private static Logger logger = LoggerFactory.getLogger(BarcodeAPI.class);

    /**
     * Get the title of the item corresponding to the barcode
     * uses the upcitemdb API
     *
     * @param barcode
     * @return
     */
    public String lookupBarcode(String barcode) throws BarcodeResponseException {

        Response response = apiSearch(barcode);
        if (response.code() == 200) {
            logger.info("Barcode lookup request succeeded");
        } else {
            logger.error("Barcode lookup request failed with response code: " + response.code());
            logger.error(response.headers().get("Retry-after"));
            throw new BarcodeResponseException("Barcode lookup request failed", response.code());
        }

        String title = "";
        try {
            title = parseJson(response.body().string());
        } catch (IOException e) {
            // TODO
        }

        if (StringUtils.hasLength(title)) {
            logger.info("Title found in barcode response: " + title);
        }

        return title;
    }


    /**
     * TODO
     * @param barcode
     * @return
     */
    private Response apiSearch(String barcode) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl httpUrl = HttpUrl.parse(url).newBuilder()
                .addQueryParameter("upc", barcode)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (Exception e) {
            logger.error("Barcode API request failed with request: " + request.url());
        }

        return response;
    }


    /**
     * TODO
     *
     * @param json
     * @return
     */
    private String parseJson(String json) {
        JsonElement root = null;
        String title = "";
        try {
            root = JsonParser.parseString(json);
        } catch (Exception e) {
            logger.error("Failed to parse Barcode API response: " + json);
        }

        try {
            title = root.getAsJsonObject()
                    .get("items")
                    .getAsJsonArray().get(0)
                    .getAsJsonObject()
                    .get("title")
                    .getAsString();
        } catch (Exception e) {
            logger.error("Failed to find title in JSON response: " + root.getAsString());
        }

        return title;
    }
}
