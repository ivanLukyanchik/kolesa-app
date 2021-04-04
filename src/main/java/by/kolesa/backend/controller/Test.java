package by.kolesa.backend.controller;

import by.kolesa.backend.dto.NotificationEmail;
import by.kolesa.backend.dto.SmsRequest;
import by.kolesa.backend.service.MailService;
import by.kolesa.backend.service.TwilioSmsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Test { // FIXME: 04.04.2021 removeMeLater

  private final TwilioSmsService twilioSmsService;
  private final MailService mailService;

  @GetMapping("/twilio")
  public void testTwilio() {
    twilioSmsService.sendSms(new SmsRequest("+375299300520", "Here we go again"));
  }

  @SneakyThrows
  @GetMapping("/gmail")
  public void testGmail() {
    mailService.sendMail(new NotificationEmail("foo", "vanek17121999@gmail.com", "tratataBody"));
  }
}
