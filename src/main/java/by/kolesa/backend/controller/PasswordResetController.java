package by.kolesa.backend.controller;

import by.kolesa.backend.dto.PasswordResetDto;
import by.kolesa.backend.dto.PasswordResetRequest;
import by.kolesa.backend.exception.CustomBadRequest;
import by.kolesa.backend.service.PasswordResetService;
import by.kolesa.backend.validator.PasswordResetRequestValidator;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@Api(tags = "Password Reset API")
@RestController
@RequiredArgsConstructor
public class PasswordResetController {

  private final PasswordResetService passwordResetService;
  private final PasswordResetRequestValidator passwordResetRequestValidator;

  @SneakyThrows
  @PostMapping("/resetPassword")
  public ResponseEntity<String> resetPassword(
      @RequestBody PasswordResetRequest passwordResetRequest) {
    if (!passwordResetRequestValidator.isValid(passwordResetRequest)) {
      throw new CustomBadRequest();
    }
    String token = passwordResetService.resetPassword(passwordResetRequest);
    return new ResponseEntity<>(
        "Link to reset password was sent to your account, token = " + token, OK);
  }

  @PostMapping("/changePassword")
  public ResponseEntity<String> changePassword(@RequestBody PasswordResetDto passwordResetDto) {
    passwordResetService.changePassword(passwordResetDto);
    return new ResponseEntity<>("Password was successfully restored", OK);
  }
}
