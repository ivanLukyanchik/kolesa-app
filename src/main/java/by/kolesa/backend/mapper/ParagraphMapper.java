package by.kolesa.backend.mapper;

import by.kolesa.backend.dto.ParagraphDto;
import by.kolesa.backend.entity.Paragraph;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {ChapterMapper.class, ArticleMapper.class})
public interface ParagraphMapper {

  ParagraphDto toParagraphDto(Paragraph paragraph);
}
