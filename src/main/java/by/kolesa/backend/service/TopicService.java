package by.kolesa.backend.service;

import by.kolesa.backend.exception.TopicNotFoundException;
import by.kolesa.backend.model.Topic;
import by.kolesa.backend.repository.TopicRepository;
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

  @Transactional(readOnly = true)
  public List<Topic> getAllTopics() {
    List<Topic> topics = new ArrayList<>();
    topicRepository.findAll().forEach(topics::add);
    return topics;
  }

  @SneakyThrows
  @Transactional(readOnly = true)
  public Topic getTopic(Long id) {
    return topicRepository.findById(id).orElseThrow(TopicNotFoundException::new);
  }
}
