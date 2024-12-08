package br.com.register.part.infrastructure.repository.impl;

import br.com.register.part.domain.model.SendEmailCommand;
import br.com.register.part.domain.model.SendEmailService;
import br.com.register.part.infrastructure.config.aws.sns.EmailSenderPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
@Log4j2
public class SendEmailServiceImpl implements SendEmailService {
    private final EmailSenderPublisher emailSenderPublisher;
    public void send(SendEmailCommand payload) {
        emailSenderPublisher.send(payload);
    }

//    public void send(SendEmailCommand payload) {
//        log.info("Enviando e-mail");
//        RestTemplate restTemplate = new RestTemplate();
//
//        var response = restTemplate.postForObject("http://localhost:8082/tools/email-sender/v1/send", payload, Object.class);
//        log.info("Send email -> {}", response);
//    }
}
