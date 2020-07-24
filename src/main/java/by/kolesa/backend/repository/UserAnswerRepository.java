package by.kolesa.backend.repository;

import by.kolesa.backend.model.UserAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends CrudRepository<UserAnswer, Long> {

    @Query(value = "SELECT * FROM USER_ANSWERS ua INNER JOIN ANSWERS a ON ua.ANSWER_ID = a.ID WHERE ua.USER_ID = :userId AND a.is_correct = :isCorrect ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<UserAnswer> findByAnswerIsCorrectAndUserId(@Param("isCorrect") boolean isCorrect, @Param("userId") Long userId, @Param("limit") int limit);

    long countByAnswerIsCorrectAndUserId(boolean isCorrect, Long userId);

    long countByUserId(Long userId);

}
