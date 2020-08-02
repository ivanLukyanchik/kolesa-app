package by.kolesa.backend.controller;

import by.kolesa.backend.dto.AuthenticationResponse;
import by.kolesa.backend.dto.LoginRequest;
import by.kolesa.backend.dto.RefreshTokenRequest;
import by.kolesa.backend.dto.RegisterRequest;
import by.kolesa.backend.exception.CustomBadRequest;
import by.kolesa.backend.exception.InvalidTokenException;
import by.kolesa.backend.exception.UserNotFoundException;
import by.kolesa.backend.service.AuthService;
import by.kolesa.backend.service.RefreshTokenService;
import by.kolesa.backend.validator.RegisterRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;

    private final RegisterRequestValidator registerRequestValidator;

    @SneakyThrows
    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@Valid @RequestBody RegisterRequest registerRequest) {
        if (!registerRequestValidator.isValid(registerRequest)) {
            throw new CustomBadRequest();
        }
        authService.signUp(registerRequest);
        return new ResponseEntity<>("User Registered successfully", OK);
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) throws UserNotFoundException, InvalidTokenException {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh token deleted successfully");
    }

}
