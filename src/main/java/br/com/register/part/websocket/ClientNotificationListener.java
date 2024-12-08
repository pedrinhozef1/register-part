package br.com.register.part.websocket;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class ClientNotificationListener {

    @SqsListener(value = "${spring.cloud.aws.sqs.client-notification-queue}")
    public void execute(String message) {
        log.info("Received event {}", message);
    }
}
