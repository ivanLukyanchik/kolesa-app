package by.kolesa.backend.controller;

import by.kolesa.backend.dto.ControlAnswersDto;
import by.kolesa.backend.dto.ControlQuestionsDto;
import by.kolesa.backend.model.Control;
import by.kolesa.backend.model.UserAnswer;
import by.kolesa.backend.service.ControlService;
import by.kolesa.backend.service.UserAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/controls")
@RequiredArgsConstructor
public class ControlController {

    private final ControlService controlService;

    private final UserAnswerService userAnswerService;

    @GetMapping("/topic/{id}")
    public ControlQuestionsDto getControlQuestionsByTopic(@PathVariable Long id) {
        return controlService.getControlQuestionsByTopic(id);
    }

    @GetMapping("/random")
    public ControlQuestionsDto getRandomControlQuestions() {
        return controlService.getRandomControlQuestions();
    }

    @GetMapping("/personalized")
    public ControlQuestionsDto getControlQuestionsBasedOnIncorrectAnswers() {
        return controlService.getControlQuestionsBasedOnIncorrectAnswers();
    }

    @PostMapping
    public void saveControlUserAnswers(@RequestBody ControlAnswersDto controlAnswersDto) {
        List<UserAnswer> answers = userAnswerService.saveUserAnswers(controlAnswersDto);
        Control control = Control.builder()
                .durationInSeconds(controlAnswersDto.getDurationInSeconds())
                .userAnswers(answers)
                .build();
        controlService.saveControlAnswers(control);
    }

//    @GetMapping()
//    public List<Control> getControls() {
//        return controlService.
//    }

}
