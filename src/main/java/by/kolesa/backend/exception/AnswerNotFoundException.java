package by.kolesa.backend.exception;

public class AnswerNotFoundException extends Exception {

  public AnswerNotFoundException() {
    this("Answer not found");
  }

  public AnswerNotFoundException(String message) {
    super(message);
  }
}
