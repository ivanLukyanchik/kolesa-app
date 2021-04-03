package by.kolesa.backend.service;

import by.kolesa.backend.exception.InvalidTokenException;
import by.kolesa.backend.model.RefreshToken;
import by.kolesa.backend.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;

  public RefreshToken generateRefreshToken() {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken.setCreatedDate(Instant.now());
    return refreshTokenRepository.save(refreshToken);
  }

  @SneakyThrows
  public void validateToken(String token) {
    refreshTokenRepository.findByToken(token).orElseThrow(InvalidTokenException::new);
  }

  public void deleteRefreshToken(String token) {
    refreshTokenRepository.deleteByToken(token);
  }
}
