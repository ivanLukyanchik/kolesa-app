package by.kolesa.backend.exception;

public class AnswerNotFoundException extends RuntimeException {

  public AnswerNotFoundException() {
    this("Answer not found");
  }

  public AnswerNotFoundException(String message) {
    super(message);
  }
}
