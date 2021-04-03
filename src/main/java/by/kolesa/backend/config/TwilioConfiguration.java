package by.kolesa.backend.config;

import com.twilio.Twilio;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
@Data
public class TwilioConfiguration {

  @Value("${twilio.account_sid}")
  private String accountSid;

  @Value("${twilio.auth_token}")
  private String authToken;

  @PostConstruct
  public void init() {
    Twilio.init(accountSid, authToken);
    log.info("Twilio initialized ... with account sid " + accountSid);
  }
}
