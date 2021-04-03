package by.kolesa.backend.exception;

public class UserNotFoundException extends Exception {

  public UserNotFoundException() {
    this("User not found");
  }

  public UserNotFoundException(String message) {
    super(message);
  }
}
