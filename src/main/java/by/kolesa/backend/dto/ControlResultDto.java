package by.kolesa.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControlResultDto {

  private List<AnswerResultDto> answers;
  private int durationInSeconds;
  private Instant createdAt;
}
