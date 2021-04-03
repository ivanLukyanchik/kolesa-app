package by.kolesa.backend.mapper;

import by.kolesa.backend.dto.ArticleDto;
import by.kolesa.backend.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleMapper {

  ArticleDto toArticleDto(Article article);
}
