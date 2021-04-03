package by.kolesa.backend.repository;

import by.kolesa.backend.model.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {

  Optional<Answer> findById(Long id);
}
