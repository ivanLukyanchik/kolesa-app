package by.kolesa.backend.validator;

import by.kolesa.backend.dto.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class RegisterRequestValidator implements Validator {

  @Override
  public boolean isValid(Object target) {
    RegisterRequest registerRequest = (RegisterRequest) target;
    String phone = registerRequest.getPhone();
    String email = registerRequest.getEmail();
    return (isNullOrEmpty(phone) && !isNullOrEmpty(email)
        || !isNullOrEmpty(phone) && isNullOrEmpty(email));
  }

  private boolean isNullOrEmpty(String str) {
    return str == null || str.trim().isEmpty();
  }
}
