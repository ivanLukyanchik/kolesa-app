package by.kolesa.backend.service;

import by.kolesa.backend.dto.TopicDto;
import by.kolesa.backend.exception.TopicNotFoundException;
import by.kolesa.backend.entity.Topic;
import by.kolesa.backend.mapper.TopicMapper;
import by.kolesa.backend.repository.TopicRepository;
import by.kolesa.backend.tools.logging.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

  private final TopicRepository topicRepository;
  private final TopicMapper topicMapper;

  @Transactional(readOnly = true)
  @LogExecutionTime
  public List<TopicDto> getAllTopics() {
    List<Topic> topics = new ArrayList<>();
    topicRepository.findAll().forEach(topics::add);
    return topicMapper.toTopicDtos(topics);
  }

  @SneakyThrows
  @Transactional(readOnly = true)
  @LogExecutionTime
  public TopicDto getTopic(Long id) {
    Topic topic = topicRepository.findById(id).orElseThrow(TopicNotFoundException::new);
    return topicMapper.toTopicDto(topic);
  }
}
