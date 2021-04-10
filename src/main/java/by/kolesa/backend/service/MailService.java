package by.kolesa.backend.service;

import by.kolesa.backend.dto.NotificationEmail;
import by.kolesa.backend.exception.SendMailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

  private final JavaMailSender mailSender;

  @Async
  public void sendMail(NotificationEmail notificationEmail) throws SendMailException {
    MimeMessagePreparator messagePreparator =
        mimeMessage -> {
          MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
          messageHelper.setFrom("kolesa.app@gmail.com");
          messageHelper.setTo(notificationEmail.getRecipient());
          messageHelper.setSubject(notificationEmail.getSubject());
          messageHelper.setText(notificationEmail.getBody());
        };
    try {
      mailSender.send(messagePreparator);
      log.info("Activation email sent to : " + notificationEmail.getRecipient());
    } catch (MailException e) {
      throw new SendMailException(
          "Exception occurred when sending mail to " + notificationEmail.getRecipient());
    }
  }
}
