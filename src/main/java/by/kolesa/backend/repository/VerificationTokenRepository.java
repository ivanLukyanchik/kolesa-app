package by.kolesa.backend.repository;

import by.kolesa.backend.model.VerificationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

  Optional<VerificationToken> findByToken(String token);
}
