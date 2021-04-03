package by.kolesa.backend.exception;

public class TopicNotFoundException extends Exception {

  public TopicNotFoundException() {
    this("Topic not found");
  }

  public TopicNotFoundException(String message) {
    super(message);
  }
}
