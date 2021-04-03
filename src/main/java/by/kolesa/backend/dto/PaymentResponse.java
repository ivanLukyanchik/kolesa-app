package by.kolesa.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

  private String status;
  private String chargeId;
  private String balanceTransaction;
}
