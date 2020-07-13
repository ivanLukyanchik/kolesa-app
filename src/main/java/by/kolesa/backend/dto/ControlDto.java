package by.kolesa.backend.dto;

import by.kolesa.backend.model.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControlDto {

    private List<Question> questions;

    private String endTime;

}
