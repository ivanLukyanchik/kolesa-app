package by.kolesa.backend.service;

import by.kolesa.backend.dto.QuestionDto;
import by.kolesa.backend.entity.Question;
import by.kolesa.backend.exception.QuestionNotFoundException;
import by.kolesa.backend.mapper.QuestionMapper;
import by.kolesa.backend.repository.QuestionRepository;
import by.kolesa.backend.tools.logging.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

  private final QuestionRepository questionRepository;
  private final QuestionMapper questionMapper;

  @Value("${control.questions.number}")
  private int controlQuestionsNumber;

  @Transactional(readOnly = true)
  @LogExecutionTime
  public List<QuestionDto> getAllQuestions() {
    List<Question> questions = new ArrayList<>();
    questionRepository.findAll().forEach(questions::add);
    return questionMapper.toQuestionDtos(questions);
  }

  @Transactional(readOnly = true)
  @LogExecutionTime
  public List<QuestionDto> getQuestionsByTopic(Long topicId) {
    return questionMapper.toQuestionDtos(questionRepository.findAllByTopicId(topicId));
  }

  @SneakyThrows
  @Transactional(readOnly = true)
  @LogExecutionTime
  public QuestionDto getQuestion(Long id) {
    Question question = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
    return questionMapper.toQuestionDto(question);
  }

  @Transactional(readOnly = true)
  @LogExecutionTime
  public List<QuestionDto> get10RandomQuestions() {
    return questionMapper.toQuestionDtos(questionRepository.findTopN(controlQuestionsNumber));
  }

  @Transactional(readOnly = true)
  @LogExecutionTime
  public List<QuestionDto> get10RandomQuestionsByTopicId(Long topicId) {
    List<Question> randomQuestionsByTopic =
        questionRepository.findTopNByTopicId(topicId, controlQuestionsNumber);
    return questionMapper.toQuestionDtos(randomQuestionsByTopic);
  }

  @Transactional(readOnly = true)
  @LogExecutionTime
  public List<QuestionDto> getQuestionsByChapterId(Long chapterId) {
    List<Question> questionsByChapterId = questionRepository.findByParagraphChapterId(chapterId);
    return questionMapper.toQuestionDtos(questionsByChapterId);
  }
}
