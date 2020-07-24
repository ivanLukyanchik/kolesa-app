package by.kolesa.backend.dto;

import by.kolesa.backend.model.Answer;
import by.kolesa.backend.model.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResultDto {

    private Question question;

    private Answer answer;

}
