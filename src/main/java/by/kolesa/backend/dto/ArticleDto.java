package by.kolesa.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ArticleDto {

  private Long id;
  private String text;

  @ApiModelProperty(value = "Can be null")
  private String linkToImage;
}
