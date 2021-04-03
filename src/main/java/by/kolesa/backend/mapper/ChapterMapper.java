package by.kolesa.backend.mapper;

import by.kolesa.backend.dto.ChapterDto;
import by.kolesa.backend.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChapterMapper {

  ChapterDto toChapterDto(Chapter chapter);

  List<ChapterDto> toChapterDtos(List<Chapter> chapters);
}
