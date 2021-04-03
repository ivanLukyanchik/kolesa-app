package by.kolesa.backend.exception;

public class IncorrectUserAnswersNotFoundException extends Exception {

  public IncorrectUserAnswersNotFoundException() {
    this("Incorrect User Answers Not Found");
  }

  public IncorrectUserAnswersNotFoundException(String message) {
    super(message);
  }
}
