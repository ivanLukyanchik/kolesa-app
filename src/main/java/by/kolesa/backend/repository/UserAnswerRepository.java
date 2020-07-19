package by.kolesa.backend.repository;

import by.kolesa.backend.model.UserAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends CrudRepository<UserAnswer, Long> {

    List<UserAnswer> findByAnswerIsCorrectAndUserId(boolean isCorrect, Long userId);

}
