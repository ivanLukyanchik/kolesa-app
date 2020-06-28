package by.kolesa.backend.exception;

public class SendMailException extends Exception {

    public SendMailException() {
        this("Exception occurred while sending email");
    }

    public SendMailException(String message) {
        super(message);
    }
}
