package br.com.register.part.infrastructure.config.aws.sns;

import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
public abstract class SnsSenderConfig {
    private final SnsTemplate snsTemplate;

    public void send(Object payload) {
        this.snsTemplate.convertAndSend(this.getTopicArn(), payload);
        log.info("[{}] sent to topic [{}]", payload.getClass().getSimpleName(), this.getTopicArn());
    }

    protected abstract String getTopicArn();
}
