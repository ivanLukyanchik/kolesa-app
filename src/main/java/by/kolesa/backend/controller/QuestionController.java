package by.kolesa.backend.controller;

import by.kolesa.backend.dto.QuestionDto;
import by.kolesa.backend.service.QuestionService;
import io.swagger.annotations.ApiOperation;
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

  @ApiOperation(value = "This is only for developing purpose", hidden = true)
  @GetMapping
  public List<QuestionDto> getAllQuestions() {
    return questionService.getAllQuestions();
  }

  @ApiOperation(value = "This is only for developing purpose", hidden = true)
  @GetMapping("/{id}")
  public QuestionDto getQuestionById(@PathVariable Long id) {
    return questionService.getQuestion(id);
  }

  @GetMapping("/topic/{id}")
  public List<QuestionDto> getQuestionsByTopic(@PathVariable("id") Long topicId) {
    return questionService.getQuestionsByTopic(topicId);
  }

  @GetMapping("/random")
  public List<QuestionDto> get10RandomQuestions() {
    return questionService.get10RandomQuestions();
  }

  @GetMapping("/topic/{id}/random")
  public List<QuestionDto> get10RandomQuestionsByTopicId(@PathVariable("id") Long topicId) {
    return questionService.get10RandomQuestionsByTopicId(topicId);
  }

  @GetMapping("/chapter/{id}")
  public List<QuestionDto> getQuestionsByChapterId(@PathVariable("id") Long chapterId) {
    return questionService.getQuestionsByChapterId(chapterId);
  }
}
