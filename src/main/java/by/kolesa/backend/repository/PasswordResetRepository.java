package by.kolesa.backend.repository;

import by.kolesa.backend.entity.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRepository extends CrudRepository<PasswordResetToken, Long> {

  Optional<PasswordResetToken> findByToken(String token);
}
