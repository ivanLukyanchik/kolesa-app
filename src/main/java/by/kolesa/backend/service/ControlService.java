package by.kolesa.backend.service;

import by.kolesa.backend.dto.AnswerResultDto;
import by.kolesa.backend.dto.ControlAnswersDto;
import by.kolesa.backend.dto.ControlQuestionsDto;
import by.kolesa.backend.dto.ControlResultDto;
import by.kolesa.backend.entity.Control;
import by.kolesa.backend.entity.Question;
import by.kolesa.backend.entity.UserAnswer;
import by.kolesa.backend.exception.IncorrectUserAnswersNotFoundException;
import by.kolesa.backend.exception.QuestionNotFoundException;
import by.kolesa.backend.mapper.AnswerMapper;
import by.kolesa.backend.mapper.QuestionMapper;
import by.kolesa.backend.repository.ControlRepository;
import by.kolesa.backend.repository.QuestionRepository;
import by.kolesa.backend.tools.logging.LogExecutionTime;
import by.kolesa.backend.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ControlService {

  private final QuestionRepository questionRepository;
  private final QuestionMapper questionMapper;
  private final AnswerMapper answerMapper;
  private final ControlRepository controlRepository;
  private final UserAnswerService userAnswerService;
  private final UserService userService;

  @Value("${control.questions.number}")
  private int controlQuestionsNumber;

  @Value("${control.duration.minutes}")
  private int controlDurationInMinutes;

  @Transactional(readOnly = true)
  @LogExecutionTime
  public ControlQuestionsDto getControlQuestionsByTopic(Long id) {
    List<Question> questions = questionRepository.findTopNByTopicId(id, controlQuestionsNumber);
    String endTime = DateUtil.getCurrentDatePlusMinutes(controlDurationInMinutes);
    return new ControlQuestionsDto(questionMapper.toQuestionDtos(questions), endTime);
  }

  @Transactional(readOnly = true)
  @LogExecutionTime
  public ControlQuestionsDto getRandomControlQuestions() {
    List<Question> questions = questionRepository.findTopN(controlQuestionsNumber);
    String endTime = DateUtil.getCurrentDatePlusMinutes(controlDurationInMinutes);
    return new ControlQuestionsDto(questionMapper.toQuestionDtos(questions), endTime);
  }

  @SneakyThrows
  @Transactional(readOnly = true)
  @LogExecutionTime
  public ControlQuestionsDto getControlQuestionsBasedOnIncorrectAnswers() {
    List<Question> questions = new ArrayList<>();
    List<UserAnswer> incorrectUserAnswers =
        userAnswerService.getIncorrectUserAnswersForPersonalizedControl();
    int actualSize = incorrectUserAnswers.size();
    if (actualSize < controlQuestionsNumber) {
      int remainingQuestionsCount = controlQuestionsNumber - actualSize;
      if (remainingQuestionsCount == controlQuestionsNumber) {
        throw new IncorrectUserAnswersNotFoundException();
      }
      questions.addAll(questionRepository.findTopN(remainingQuestionsCount));
    }
    for (UserAnswer incorrectUserAnswer : incorrectUserAnswers) {
      Question question =
          questionRepository
              .findById(incorrectUserAnswer.getQuestionId())
              .orElseThrow(QuestionNotFoundException::new);
      questions.add(question);
    }
    String endTime = DateUtil.getCurrentDatePlusMinutes(controlDurationInMinutes);
    return new ControlQuestionsDto(questionMapper.toQuestionDtos(questions), endTime);
  }

  @Transactional
  @LogExecutionTime
  public void saveControlUserAnswers(ControlAnswersDto controlAnswersDto) {
    Control control = buildControl(controlAnswersDto);
    List<UserAnswer> userAnswers = userAnswerService.getUserAnswersFromDto(controlAnswersDto);
    for (UserAnswer userAnswer : userAnswers) {
      control.addUserAnswer(userAnswer);
    }
    controlRepository.save(control);
  }

  @SneakyThrows
  @Transactional(readOnly = true)
  @LogExecutionTime
  public List<ControlResultDto> getPassedControlsForLoggedInUser() {
    List<ControlResultDto> controlResults = new ArrayList<>();
    ControlResultDto controlResultDto;
    Long userId = userService.getUserIdOfLoggedIn();
    List<Control> controls = controlRepository.findByUserId(userId);
    for (Control control : controls) {
      controlResultDto = new ControlResultDto();
      controlResultDto.setCreatedAt(control.getCreatedAt());
      controlResultDto.setDurationInSeconds(control.getDurationInSeconds());
      List<UserAnswer> userAnswers = control.getUserAnswers();
      List<AnswerResultDto> answerResults = new ArrayList<>();
      for (UserAnswer userAnswer : userAnswers) {
        AnswerResultDto answerResultDto = new AnswerResultDto();
        Question question =
            questionRepository
                .findById(userAnswer.getQuestionId())
                .orElseThrow(QuestionNotFoundException::new);
        answerResultDto.setQuestion(questionMapper.toQuestionDto(question));
        answerResultDto.setAnswer(answerMapper.toAnswerDto(userAnswer.getAnswer()));
        answerResults.add(answerResultDto);
      }
      controlResultDto.setAnswers(answerResults);
      controlResults.add(controlResultDto);
    }
    return controlResults;
  }

  @Transactional(readOnly = true)
  @LogExecutionTime
  public String calculatePercentageOfCorrectAnswers() {
    Long userId = userService.getUserIdOfLoggedIn();
    long allUserAnswersCount = userAnswerService.countAllUserAnswers(userId);
    if (allUserAnswersCount == 0) {
      return String.format("%.2f", 0.0);
    }
    long correctUserAnswersCount = userAnswerService.countCorrectUserAnswers(userId);
    double percentage = ((double) correctUserAnswersCount / allUserAnswersCount) * 100;
    return String.format("%.2f", percentage);
  }

  private Control buildControl(ControlAnswersDto controlAnswersDto) {
    return Control.builder()
        .durationInSeconds(controlAnswersDto.getDurationInSeconds())
        .userAnswers(new ArrayList<>())
        .userId(userService.getUserIdOfLoggedIn())
        .build();
  }
}
