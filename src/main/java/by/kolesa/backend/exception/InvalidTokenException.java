package by.kolesa.backend.exception;

public class InvalidTokenException extends RuntimeException {

  public InvalidTokenException() {
    this("Token is invalid");
  }

  public InvalidTokenException(String message) {
    super(message);
  }
}
