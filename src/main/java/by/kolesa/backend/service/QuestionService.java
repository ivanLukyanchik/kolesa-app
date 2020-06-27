package by.kolesa.backend.service;

import by.kolesa.backend.model.Question;
import by.kolesa.backend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        questionRepository.findAll().forEach(questions::add);
        return questions;
    }

    public List<Question> getQuestionsByTopic(Long topicId) {
        return questionRepository.findAllByTopicId(topicId);
    }

    public Question getQuestion(Long id) {
        return questionRepository.findById(id).get();
    }

}
