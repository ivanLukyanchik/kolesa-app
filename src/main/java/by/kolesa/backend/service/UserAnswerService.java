package by.kolesa.backend.service;

import by.kolesa.backend.dto.ControlAnswersDto;
import by.kolesa.backend.dto.UserAnswerDto;
import by.kolesa.backend.model.Answer;
import by.kolesa.backend.model.UserAnswer;
import by.kolesa.backend.repository.UserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAnswerService {

  private final UserAnswerRepository userAnswerRepository;
  private final AnswerService answerService;
  private final UserService userService;

  @Value("${control.questions.number}")
  private int controlQuestionsNumber;

  @Transactional(readOnly = true)
  public List<UserAnswer> getIncorrectUserAnswersForPersonalizedControl() {
    Long userId = userService.getUserIdOfLoggedIn();
    return userAnswerRepository.findByAnswerIsCorrectAndUserIdAndForControl(
        false, userId, controlQuestionsNumber);
  }

  @Transactional(readOnly = true)
  public long countCorrectUserAnswers(Long userId) {
    return userAnswerRepository.countByAnswerIsCorrectAndUserId(true, userId);
  }

  @Transactional(readOnly = true)
  public List<UserAnswer> getUserAnswersFromDto(ControlAnswersDto controlAnswersDto) {
    UserAnswer userAnswer;
    List<UserAnswer> answers = new ArrayList<>();
    for (UserAnswerDto userAnswerDto : controlAnswersDto.getUserAnswers()) {
      userAnswer = new UserAnswer();
      Long userId = userService.getUserIdOfLoggedIn();
      userAnswer.setUserId(userId);
      userAnswer.setQuestionId(userAnswerDto.getQuestionId());
      if (userAnswerDto.getAnswerId() != null) {
        Answer answer = answerService.getAnswerById(userAnswerDto.getAnswerId());
        userAnswer.setAnswer(answer);
        if (answer.isCorrect()) {
          List<UserAnswer> incorrectUserAnswersForThisQuestion =
              userAnswerRepository.findByQuestionIdAndUserIdAndAnswerIsCorrectAndForControl(
                  userAnswerDto.getQuestionId(), userId, false, true);
          for (UserAnswer incorrectUserAnswer : incorrectUserAnswersForThisQuestion) {
            incorrectUserAnswer.setForControl(false);
            userAnswerRepository.save(incorrectUserAnswer);
          }
        }
      } else {
        userAnswer.setAnswer(null);
      }
      answers.add(userAnswer);
    }
    return answers;
  }

  public long countAllUserAnswers(Long userId) {
    return userAnswerRepository.countByUserIdAndAnswerNotNull(userId);
  }
}
