package sf.inventory.external.exception;

public class BarcodeResponseException extends Exception {

    private int responseCode;

    public BarcodeResponseException(String message, int responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
