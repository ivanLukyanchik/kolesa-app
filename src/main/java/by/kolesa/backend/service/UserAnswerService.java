package by.kolesa.backend.service;

import by.kolesa.backend.dto.ControlAnswersDto;
import by.kolesa.backend.dto.UserAnswerDto;
import by.kolesa.backend.model.UserAnswer;
import by.kolesa.backend.repository.UserAnswerRepository;
import lombok.RequiredArgsConstructor;
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

    public List<UserAnswer> getIncorrectUserAnswers() {
        Long userId = userService.getUserIdOfLoggedIn();
        return userAnswerRepository.findByAnswerIsCorrectAndUserId(false, userId);
    }

    @Transactional
    public List<UserAnswer> saveUserAnswers(ControlAnswersDto controlAnswersDto) {
        UserAnswer userAnswer = new UserAnswer();
        List<UserAnswer> answers = new ArrayList<>();
        for (UserAnswerDto userAnswerDto : controlAnswersDto.getUserAnswers()) {
            userAnswer.setQuestionId(userAnswerDto.getQuestionId());
            userAnswer.setAnswer(answerService.getAnswerById(userAnswerDto.getAnswerId()));
            Long userId = userService.getUserIdOfLoggedIn();
            userAnswer.setUserId(userId);
            userAnswerRepository.save(userAnswer);
            answers.add(userAnswer);
        }
        return answers;
    }

}
