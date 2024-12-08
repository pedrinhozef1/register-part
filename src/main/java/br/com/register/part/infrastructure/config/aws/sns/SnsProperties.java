package br.com.register.part.infrastructure.config.aws.sns;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class SnsProperties {
    @Value(value = "${spring.cloud.aws.sns.sent-email-topic}")
    public String sentEmailTopic;
}
