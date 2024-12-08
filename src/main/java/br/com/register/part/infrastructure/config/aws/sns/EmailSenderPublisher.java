package br.com.register.part.infrastructure.config.aws.sns;

import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;


@Getter
@Log4j2
public class EmailSenderPublisher extends SnsSenderConfig {
    private final String topicArn;

    public EmailSenderPublisher(SnsTemplate snsTemplate, SnsProperties snsProperties) {
        super(snsTemplate);
        this.topicArn = snsProperties.getSentEmailTopic();
    }
}
