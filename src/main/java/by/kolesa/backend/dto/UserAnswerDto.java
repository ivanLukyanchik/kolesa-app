package by.kolesa.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerDto {

  private Long questionId;

  @ApiModelProperty(value = "Null if user didn't answer to this question")
  private Long answerId;
}
