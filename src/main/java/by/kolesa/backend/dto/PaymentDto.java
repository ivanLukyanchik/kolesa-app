package by.kolesa.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

  private int amount;
  private String currency;
  private String description;
  private String stripeEmail;
  private String stripeToken;
}
