package by.kolesa.backend.exception;

public class CustomBadRequest extends Exception {

    public CustomBadRequest() {
        this("Either email or phone number must be specified");
    }

    public CustomBadRequest(String message) {
        super(message);
    }

}
