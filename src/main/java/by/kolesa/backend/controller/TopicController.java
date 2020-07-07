package by.kolesa.backend.controller;

import by.kolesa.backend.model.Topic;
import by.kolesa.backend.service.TopicService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping()
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @ApiOperation(value = "This is only for developing purpose", hidden = true)
    @GetMapping("/{id}")
    public Topic getTopicById(@PathVariable Long id) {
        return topicService.getTopic(id);
    }
}
