package by.kolesa.backend.controller;

import by.kolesa.backend.dto.SmsRequest;
import by.kolesa.backend.service.TwilioSmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
public class SmsController {

    private final TwilioSmsService twilioService;

    @PostMapping
    public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
        twilioService.sendSms(smsRequest);
    }
}
