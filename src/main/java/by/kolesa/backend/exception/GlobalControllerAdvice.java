package by.kolesa.backend.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
@RestController
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public String handleBadRequestException() {
        return "It is a bad request!";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException() {
        return "User Not Found";
    }

    @ExceptionHandler(SendMailException.class)
    public String handleSendMailException() {
        return "Exception occurred while sending mail";
    }

    @ExceptionHandler(LoadingKeystoreException.class)
    public String handleLoadingKeystoreException() {
        return "Exception occurred while loading keystore";
    }

    @ExceptionHandler(InvalidTokenException.class)
    public String handleInvalidTokenException() {
        return "Token is invalid";
    }

    @ExceptionHandler(CustomBadRequest.class)
    public String handleCustomBadRequest(CustomBadRequest ex) {
        return ex.getMessage();
    }

}
