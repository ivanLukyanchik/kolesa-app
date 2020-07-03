package by.kolesa.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;

    public String buildForSignUp(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("sign-up", context);
    }

    public String buildForPasswordReset(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("password-reset", context);
    }

}
