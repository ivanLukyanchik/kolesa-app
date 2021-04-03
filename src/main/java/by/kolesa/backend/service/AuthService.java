package by.kolesa.backend.service;

import by.kolesa.backend.dto.AuthenticationResponse;
import by.kolesa.backend.dto.LoginRequest;
import by.kolesa.backend.dto.NotificationEmail;
import by.kolesa.backend.dto.RefreshTokenRequest;
import by.kolesa.backend.dto.RegisterRequest;
import by.kolesa.backend.dto.SmsRequest;
import by.kolesa.backend.exception.CustomBadRequest;
import by.kolesa.backend.exception.InvalidTokenException;
import by.kolesa.backend.exception.UserNotFoundException;
import by.kolesa.backend.model.User;
import by.kolesa.backend.model.VerificationToken;
import by.kolesa.backend.repository.UserRepository;
import by.kolesa.backend.repository.VerificationTokenRepository;
import by.kolesa.backend.security.JwtProvider;
import by.kolesa.backend.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final VerificationTokenRepository verificationTokenRepository;
  private final MailContentBuilder mailContentBuilder;
  private final MailService mailService;
  private final AuthenticationManager authenticationManager;
  private final JwtProvider jwtProvider;
  private final RefreshTokenService refreshTokenService;
  private final TwilioSmsService twilioSmsService;
  private final UserValidator userValidator;

  @Value("${mail.activation.url}")
  private String activationUrl;

  @SneakyThrows
  @Transactional
  public void signUp(RegisterRequest registerRequest) {
    User user = new User();
    user.setUsername(registerRequest.getUsername());
    if (registerRequest.getEmail() != null) {
      if (!registerRequest.getEmail().isBlank()) {
        user.setEmail(registerRequest.getEmail());
        user.setRegisteredByEmail(true);
      }
    } else {
      user.setPhoneNumber(registerRequest.getPhone());
      user.setRegisteredByEmail(false);
    }
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setCreatedDate(Instant.now());
    user.setEnabled(true);

    if (!userValidator.isValid(user)) {
      throw new CustomBadRequest(userValidator.parseErrorMessages());
    }
    userRepository.save(user);

    sendActivationCode(user);
  }

  @SneakyThrows
  private void sendActivationCode(User user) {
    String token;
    if (user.isRegisteredByEmail()) {
      token = generateMailVerificationToken(user);
      String message = mailContentBuilder.buildForSignUp(activationUrl + "/" + token);
      mailService.sendMail(
          new NotificationEmail("Please Activate your account", user.getEmail(), message));
    } else {
      token = generateMobileVerificationToken(user);
      twilioSmsService.sendSms(
          new SmsRequest(user.getPhoneNumber(), token + " is your \"Kolesa\" authentication code"));
    }
  }

  private String generateMailVerificationToken(User user) {
    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken(token);
    verificationToken.setUser(user);
    verificationTokenRepository.save(verificationToken);
    return token;
  }

  private String generateMobileVerificationToken(User user) {
    Random random = new Random();
    int code = random.nextInt((99999 - 10000) + 1) + 10000;
    String token = String.valueOf(code);
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken(token);
    verificationToken.setUser(user);
    verificationTokenRepository.save(verificationToken);
    return token;
  }

  @Transactional
  public void verifyAccount(String token) throws InvalidTokenException, UserNotFoundException {
    Optional<VerificationToken> verificationTokenOptional =
        verificationTokenRepository.findByToken(token);
    VerificationToken verificationToken =
        verificationTokenOptional.orElseThrow(InvalidTokenException::new);
    fetchUserAndEnable(verificationToken);
    verificationTokenRepository.delete(verificationToken);
  }

  private void fetchUserAndEnable(VerificationToken verificationToken) throws UserNotFoundException {
    String username = verificationToken.getUser().getUsername();
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found with name : " + username));
    user.setEnabled(true);
    userRepository.save(user);
  }

  public AuthenticationResponse login(LoginRequest loginRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtProvider.generateToken(authentication);
    return AuthenticationResponse.builder()
        .authenticationToken(token)
        .refreshToken(refreshTokenService.generateRefreshToken().getToken())
        .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpiration()))
        .username(loginRequest.getUsername())
        .build();
  }

  public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
    refreshTokenService.validateToken(refreshTokenRequest.getRefreshToken());
    String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
    return AuthenticationResponse.builder()
        .username(refreshTokenRequest.getUsername())
        .refreshToken(refreshTokenRequest.getRefreshToken())
        .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpiration()))
        .authenticationToken(token)
        .build();
  }
}
