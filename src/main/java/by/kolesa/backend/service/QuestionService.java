package by.kolesa.backend.service;

import by.kolesa.backend.exception.QuestionNotFoundException;
import by.kolesa.backend.model.Question;
import by.kolesa.backend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Value("${control.questions.number}")
    private String CONTROL_QUESTIONS_NUMBER;

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        questionRepository.findAll().forEach(questions::add);
        return questions;
    }

    public List<Question> getQuestionsByTopic(Long topicId) {
        return questionRepository.findAllByTopicId(topicId);
    }

    @SneakyThrows
    public Question getQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
    }

    public List<Question> get10RandomQuestions() {
        return questionRepository.findTopN(Integer.parseInt(CONTROL_QUESTIONS_NUMBER));
    }

    public List<Question> get10RandomQuestionsByTopicId(Long topicId) {
        return questionRepository.findTopNByTopicId(topicId, Integer.parseInt(CONTROL_QUESTIONS_NUMBER));
    }

    public List<Question> getQuestionsByChapterId(Long chapterId) {
        return questionRepository.findByParagraphChapterId(chapterId);
    }

}
