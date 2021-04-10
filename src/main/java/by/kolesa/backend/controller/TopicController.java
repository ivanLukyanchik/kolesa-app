package by.kolesa.backend.controller;

import by.kolesa.backend.dto.TopicDto;
import by.kolesa.backend.service.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "Topic API")
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController {

  private final TopicService topicService;

  @GetMapping
  public List<TopicDto> getAllTopics() {
    return topicService.getAllTopics();
  }

  @ApiOperation(value = "This is only for developing purpose", hidden = true)
  @GetMapping("/{id}")
  public TopicDto getTopicById(@PathVariable Long id) {
    return topicService.getTopic(id);
  }
}
