package by.kolesa.backend.exception;

public class IncorrectUserAnswersNotFoundException extends RuntimeException {

  public IncorrectUserAnswersNotFoundException() {
    this("Incorrect User Answers Not Found");
  }

  public IncorrectUserAnswersNotFoundException(String message) {
    super(message);
  }
}
