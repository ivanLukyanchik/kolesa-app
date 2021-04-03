package by.kolesa.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ParagraphDto {

  private Long id;
  private String text;
  private ChapterDto chapter;

  @ApiModelProperty(value = "Can be null")
  private String linkToImage;

  private List<ArticleDto> articles;
}
