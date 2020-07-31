package by.kolesa.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @ApiModelProperty(value = "Either email or phone must be specified")
    private String email;

    private String phone;

    private String username;

    private String password;

}
