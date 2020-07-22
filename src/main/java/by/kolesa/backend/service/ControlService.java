package by.kolesa.backend.service;

import by.kolesa.backend.dto.ControlQuestionsDto;
import by.kolesa.backend.exception.QuestionNotFoundException;
import by.kolesa.backend.model.Control;
import by.kolesa.backend.model.Question;
import by.kolesa.backend.model.UserAnswer;
import by.kolesa.backend.repository.ControlRepository;
import by.kolesa.backend.repository.QuestionRepository;
import by.kolesa.backend.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ControlService {

    private final QuestionRepository questionRepository;

    private final ControlRepository controlRepository;

    private final UserAnswerService userAnswerService;

    private final UserService userService;

    public ControlQuestionsDto getControlQuestionsByTopic(Long id) {
        List<Question> questions = questionRepository.findTopNByTopicId(id, 10);
        String endTime = DateUtil.getCurrentDatePlusMinutes(15);
        return new ControlQuestionsDto(questions, endTime);
    }

    public ControlQuestionsDto getRandomControlQuestions() {
        List<Question> questions = questionRepository.findTopN(10);
        String endTime = DateUtil.getCurrentDatePlusMinutes(15);
        return new ControlQuestionsDto(questions, endTime);
    }

    @SneakyThrows
    public ControlQuestionsDto getControlQuestionsBasedOnIncorrectAnswers() {
        List<Question> questions = new ArrayList<>();
        List<UserAnswer> incorrectUserAnswers = userAnswerService.getIncorrectUserAnswers();
        for (UserAnswer incorrectUserAnswer : incorrectUserAnswers) {
            Question question = questionRepository.findById(incorrectUserAnswer.getQuestionId()).orElseThrow(QuestionNotFoundException::new);
            questions.add(question);
        }
        String endTime = DateUtil.getCurrentDatePlusMinutes(15);
        return new ControlQuestionsDto(questions, endTime);
    }

    @Transactional
    public void saveControlAnswers(Control control) {
        controlRepository.save(control);
    }

    public List<Control> getControlsForLoggedInUser() {
        Long userId = userService.getUserIdOfLoggedIn();
        return controlRepository.findByUserAnswersUserId(userId);
    }

}
