package by.kolesa.backend.mapper;

import by.kolesa.backend.dto.TopicDto;
import by.kolesa.backend.entity.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TopicMapper {

  TopicDto toTopicDto(Topic topic);

  List<TopicDto> toTopicDtos(List<Topic> topics);
}
