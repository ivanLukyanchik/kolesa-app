package by.kolesa.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequest {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String message;

}
