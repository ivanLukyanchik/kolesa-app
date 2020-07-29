package by.kolesa.backend.service;

import by.kolesa.backend.dto.ControlAnswersDto;
import by.kolesa.backend.dto.UserAnswerDto;
import by.kolesa.backend.model.Answer;
import by.kolesa.backend.model.UserAnswer;
import by.kolesa.backend.repository.UserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;

    private final AnswerService answerService;

    private final UserService userService;

    @Value("${control.questions.number}")
    private String CONTROL_QUESTIONS_NUMBER;

    public List<UserAnswer> getIncorrectUserAnswersForPersonalizedControl() {
        Long userId = userService.getUserIdOfLoggedIn();
        return userAnswerRepository.findByAnswerIsCorrectAndUserIdAndForControl(false, userId, Integer.parseInt(CONTROL_QUESTIONS_NUMBER));
    }

    public long countCorrectUserAnswers(Long userId) {
        return userAnswerRepository.countByAnswerIsCorrectAndUserId(true, userId);
    }

    @Transactional
    public List<UserAnswer> getUserAnswersFromDto(ControlAnswersDto controlAnswersDto) {
        UserAnswer userAnswer;
        List<UserAnswer> answers = new ArrayList<>();
        for (UserAnswerDto userAnswerDto : controlAnswersDto.getUserAnswers()) {
            userAnswer = new UserAnswer();
            userAnswer.setQuestionId(userAnswerDto.getQuestionId());
            Answer answer = answerService.getAnswerById(userAnswerDto.getAnswerId());
            userAnswer.setAnswer(answer);
            Long userId = userService.getUserIdOfLoggedIn();
            userAnswer.setUserId(userId);
            if (answer.isCorrect()) {
                List<UserAnswer> incorrectUserAnswersForThisQuestion =
                        userAnswerRepository.findByQuestionIdAndUserIdAndAnswerIsCorrectAndForControl(userAnswerDto.getQuestionId(),
                                userId, false, true);
                for (UserAnswer incorrectUserAnswer : incorrectUserAnswersForThisQuestion) {
                    incorrectUserAnswer.setForControl(false);
                    userAnswerRepository.save(incorrectUserAnswer);
                }
            }
            answers.add(userAnswer);
        }
        return answers;
    }

    public long countAllUserAnswers(Long userId) {
        return userAnswerRepository.countByUserId(userId);
    }

}
