package by.kolesa.backend.validator;

import by.kolesa.backend.dto.PasswordResetRequest;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetRequestValidator implements Validator {

    @Override
    public boolean isValid(Object target) {
        PasswordResetRequest passwordResetRequest = (PasswordResetRequest) target;
        String phone = passwordResetRequest.getPhoneNumber();
        String email = passwordResetRequest.getEmail();
        return (isNullOrEmpty(phone) && !isNullOrEmpty(email) || !isNullOrEmpty(phone) && isNullOrEmpty(email));
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}
