package by.kolesa.backend.controller;

import by.kolesa.backend.model.Question;
import by.kolesa.backend.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping()
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable Long id) {
        return questionService.getQuestion(id);
    }

    @GetMapping("/topic/{id}")
    public List<Question> getQuestionsByTopic(@PathVariable("id") Long topicId) {
        return questionService.getQuestionsByTopic(topicId);
    }

}
