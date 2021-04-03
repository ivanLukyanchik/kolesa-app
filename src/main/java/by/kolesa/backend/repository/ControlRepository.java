package by.kolesa.backend.repository;

import by.kolesa.backend.model.Control;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ControlRepository extends CrudRepository<Control, Long> {

  List<Control> findByUserId(Long userId);
}
