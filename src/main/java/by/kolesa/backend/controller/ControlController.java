package by.kolesa.backend.controller;

import by.kolesa.backend.dto.ControlAnswersDto;
import by.kolesa.backend.dto.ControlQuestionsDto;
import by.kolesa.backend.dto.ControlResultDto;
import by.kolesa.backend.service.ControlService;
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

    @GetMapping("/percentage")
    public String calculatePercentageOfCorrectAnswers() {
        return controlService.calculatePercentageOfCorrectAnswers();
    }

    @PostMapping
    public void saveControlUserAnswers(@RequestBody ControlAnswersDto controlAnswersDto) {
        controlService.saveControlUserAnswers(controlAnswersDto);
    }

    @GetMapping
    public List<ControlResultDto> getPassedControlsForLoggedInUser() {
        return controlService.getPassedControlsForLoggedInUser();
    }

}
