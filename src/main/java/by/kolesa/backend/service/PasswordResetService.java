package by.kolesa.backend.service;

import by.kolesa.backend.dto.NotificationEmail;
import by.kolesa.backend.dto.PasswordResetDto;
import by.kolesa.backend.dto.PasswordResetRequest;
import by.kolesa.backend.dto.SmsRequest;
import by.kolesa.backend.exception.InvalidTokenException;
import by.kolesa.backend.exception.UserNotFoundException;
import by.kolesa.backend.entity.PasswordResetToken;
import by.kolesa.backend.entity.User;
import by.kolesa.backend.repository.PasswordResetRepository;
import by.kolesa.backend.repository.UserRepository;
import by.kolesa.backend.tools.logging.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

  private final PasswordResetRepository passwordResetRepository;
  private final UserRepository userRepository;
  private final MailContentBuilder mailContentBuilder;
  private final MailService mailService;
  private final PasswordEncoder passwordEncoder;
  private final TwilioSmsService twilioSmsService;

  @Value("${mail.restore.url}")
  private String restoreUrl;

  @SneakyThrows
  @LogExecutionTime
  public String resetPassword(PasswordResetRequest passwordResetRequest) {
    Optional<User> userOptional = Optional.empty();
    if (passwordResetRequest.getEmail() != null && !passwordResetRequest.getEmail().isBlank()) {
      userOptional = userRepository.findByEmail(passwordResetRequest.getEmail());
    }
    if (userOptional.isEmpty()) {
      userOptional = userRepository.findByPhoneNumber(passwordResetRequest.getPhoneNumber());
    }
    User user = userOptional.orElseThrow(UserNotFoundException::new);
    return sendPasswordResetCode(user);
  }

  @SneakyThrows
  @LogExecutionTime
  private String sendPasswordResetCode(User user) {
    String token;
    if (user.isRegisteredByEmail()) {
      token = generateMailPasswordResetToken(user);
      String message = mailContentBuilder.buildForPasswordReset(restoreUrl + "/" + token);
      mailService.sendMail(new NotificationEmail("Password reset", user.getEmail(), message));
    } else {
      token = generateMobilePasswordResetToken(user);
      twilioSmsService.sendSms(
          new SmsRequest(user.getPhoneNumber(), token + " is your \"Kolesa\" authentication code"));
    }
    return token;
  }

  private String generateMailPasswordResetToken(User user) {
    String token = UUID.randomUUID().toString();
    PasswordResetToken passwordResetToken = new PasswordResetToken();
    passwordResetToken.setToken(token);
    passwordResetToken.setUser(user);
    passwordResetRepository.save(passwordResetToken);
    return token;
  }

  private String generateMobilePasswordResetToken(User user) {
    Random random = new Random();
    int code = random.nextInt((99999 - 10000) + 1) + 10000;
    String token = String.valueOf(code);
    PasswordResetToken passwordResetToken = new PasswordResetToken();
    passwordResetToken.setToken(token);
    passwordResetToken.setUser(user);
    passwordResetRepository.save(passwordResetToken);
    return token;
  }

  @SneakyThrows
  @Transactional
  @LogExecutionTime
  public void changePassword(PasswordResetDto passwordResetDto) {
    Optional<PasswordResetToken> passwordResetTokenOptional =
        passwordResetRepository.findByToken(passwordResetDto.getToken());
    PasswordResetToken passwordResetToken =
        passwordResetTokenOptional.orElseThrow(InvalidTokenException::new);
    User user = passwordResetToken.getUser();
    user.setPassword(passwordEncoder.encode(passwordResetDto.getNewPassword()));
    userRepository.save(user);
    passwordResetRepository.delete(passwordResetToken);
  }
}
