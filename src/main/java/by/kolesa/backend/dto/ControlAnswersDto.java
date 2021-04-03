package by.kolesa.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControlAnswersDto {

  private List<UserAnswerDto> userAnswers;
  private int durationInSeconds;
}
