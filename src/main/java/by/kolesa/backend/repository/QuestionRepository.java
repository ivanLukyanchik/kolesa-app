package by.kolesa.backend.repository;

import by.kolesa.backend.model.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

    List<Question> findAllByTopicId(Long topicId);

    List<Question> findByParagraphChapterId(Long chapterId);

    @Query(value = "SELECT * FROM QUESTIONS WHERE TOPIC_ID = :topicId ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Question> findTopNByTopicId(@Param("topicId") Long topicId, @Param("limit") int limit);

    @Query(value = "SELECT * FROM QUESTIONS ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Question> findTopN(@Param("limit") int limit);

    Optional<Question> findById(Long id);

}
