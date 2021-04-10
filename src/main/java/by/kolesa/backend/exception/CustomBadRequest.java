package by.kolesa.backend.exception;

public class CustomBadRequest extends RuntimeException {

  public CustomBadRequest() {
    this("Either email or phone number must be specified");
  }

  public CustomBadRequest(String message) {
    super(message);
  }
}
