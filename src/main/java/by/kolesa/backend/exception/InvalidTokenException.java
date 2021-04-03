package by.kolesa.backend.exception;

public class InvalidTokenException extends Exception {

  public InvalidTokenException() {
    this("Token is invalid");
  }

  public InvalidTokenException(String message) {
    super(message);
  }
}
