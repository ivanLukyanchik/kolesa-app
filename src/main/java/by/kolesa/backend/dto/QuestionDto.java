package by.kolesa.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QuestionDto {

  private Long id;
  private String text;
  private String linkToImage;
  private Long topicId;
  private ParagraphDto paragraph;
  private List<AnswerDto> answers;
}
