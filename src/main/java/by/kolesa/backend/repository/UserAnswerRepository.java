package by.kolesa.backend.repository;

import by.kolesa.backend.entity.UserAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends CrudRepository<UserAnswer, Long> {

  @Query(
      value =
          "SELECT * FROM USER_ANSWERS ua"
              + " INNER JOIN ANSWERS a"
              + " ON ua.ANSWER_ID = a.ID"
              + " WHERE ua.USER_ID = :userId"
              + " AND a.is_correct = :isCorrect"
              + " AND ua.FOR_CONTROL = TRUE"
              + " ORDER BY RAND() LIMIT :limit",
      nativeQuery = true)
  List<UserAnswer> findByAnswerIsCorrectAndUserIdAndForControl(
      @Param("isCorrect") boolean isCorrect,
      @Param("userId") Long userId,
      @Param("limit") int limit);

  long countByAnswerIsCorrectAndUserId(boolean isCorrect, Long userId);

  long countByUserIdAndAnswerNotNull(Long userId);

  List<UserAnswer> findByQuestionIdAndUserIdAndAnswerIsCorrectAndForControl(
      Long questionId, Long userId, boolean isCorrect, boolean forControl);
}
