package by.kolesa.backend.exception;

public class QuestionNotFoundException extends RuntimeException {

  public QuestionNotFoundException() {
    this("Question not found");
  }

  public QuestionNotFoundException(String message) {
    super(message);
  }
}
