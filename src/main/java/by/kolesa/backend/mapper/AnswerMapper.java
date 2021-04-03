package by.kolesa.backend.mapper;

import by.kolesa.backend.dto.AnswerDto;
import by.kolesa.backend.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {

  AnswerDto toAnswerDto(Answer answer);

  List<AnswerDto> toAnswerDtos(List<Answer> answers);
}
