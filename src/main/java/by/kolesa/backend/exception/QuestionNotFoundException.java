package by.kolesa.backend.exception;

public class QuestionNotFoundException extends Exception {

    public QuestionNotFoundException() {
        this("Question not found");
    }

    public QuestionNotFoundException(String message) {
        super(message);
    }

}
