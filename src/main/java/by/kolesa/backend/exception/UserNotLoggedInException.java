package by.kolesa.backend.exception;

public class UserNotLoggedInException extends Exception {

    public UserNotLoggedInException() {
        this("User is not logged in");
    }

    public UserNotLoggedInException(String message) {
        super(message);
    }

}
