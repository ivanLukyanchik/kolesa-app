package by.kolesa.backend.mapper;

import by.kolesa.backend.dto.QuestionDto;
import by.kolesa.backend.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {ParagraphMapper.class, AnswerMapper.class})
public interface QuestionMapper {

  QuestionDto toQuestionDto(Question question);

  List<QuestionDto> toQuestionDtos(List<Question> questions);
}
