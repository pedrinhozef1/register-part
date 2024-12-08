package br.com.register.part.infrastructure.listener;

import br.com.register.part.websocket.WebSocketDto;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class EmailDomainEventListener {

    private final SimpMessagingTemplate template;


    @SqsListener(value = "${spring.cloud.aws.sqs.email-domain-event-listener}")
    public void execute(String message) {
        log.info("Received email response event {}", message);
        template.convertAndSend("/topic/message", new WebSocketDto("E-mail de confirmação enviado com sucesso"));
    }
}
