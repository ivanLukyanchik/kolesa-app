package by.kolesa.backend.service;

import by.kolesa.backend.exception.AnswerNotFoundException;
import by.kolesa.backend.model.Answer;
import by.kolesa.backend.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

  private final AnswerRepository answerRepository;

  @Transactional(readOnly = true)
  public List<Answer> getAllAnswers() {
    List<Answer> answers = new ArrayList<>();
    answerRepository.findAll().forEach(answers::add);
    return answers;
  }

  @SneakyThrows
  @Transactional(readOnly = true)
  public Answer getAnswer(Long id) {
    return answerRepository.findById(id).orElseThrow(AnswerNotFoundException::new);
  }

  @SneakyThrows
  @Transactional(readOnly = true)
  public Answer getAnswerById(Long id) {
    return answerRepository.findById(id).orElseThrow(AnswerNotFoundException::new);
  }
}
