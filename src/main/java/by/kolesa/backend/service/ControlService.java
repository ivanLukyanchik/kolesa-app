package by.kolesa.backend.service;

import by.kolesa.backend.dto.ControlDto;
import by.kolesa.backend.model.Question;
import by.kolesa.backend.repository.QuestionRepository;
import by.kolesa.backend.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ControlService {

    private final QuestionRepository questionRepository;

    public ControlDto getControlByTopic(Long id) {
        List<Question> questions = questionRepository.findTopNByTopicId(id, 10);
        String endTime = DateUtil.getCurrentDatePlusMinutes(15);
        return new ControlDto(questions, endTime);
    }

    public ControlDto getRandomControl() {
        List<Question> questions = questionRepository.findTopN(10);
        String endTime = DateUtil.getCurrentDatePlusMinutes(15);
        return new ControlDto(questions, endTime);
    }

}
