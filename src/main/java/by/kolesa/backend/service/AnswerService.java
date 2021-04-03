package by.kolesa.backend.service;

import by.kolesa.backend.dto.AnswerDto;
import by.kolesa.backend.entity.Answer;
import by.kolesa.backend.exception.AnswerNotFoundException;
import by.kolesa.backend.mapper.AnswerMapper;
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
  private final AnswerMapper answerMapper;

  @Transactional(readOnly = true)
  public List<AnswerDto> getAllAnswers() {
    List<Answer> answers = new ArrayList<>();
    answerRepository.findAll().forEach(answers::add);
    return answerMapper.toAnswerDtos(answers);
  }

  @SneakyThrows
  @Transactional(readOnly = true)
  public AnswerDto getAnswer(Long id) {
    Answer answer = answerRepository.findById(id).orElseThrow(AnswerNotFoundException::new);
    return answerMapper.toAnswerDto(answer);
  }

  @SneakyThrows
  @Transactional(readOnly = true)
  public Answer getAnswerById(Long id) {
    return answerRepository.findById(id).orElseThrow(AnswerNotFoundException::new);
  }
}
