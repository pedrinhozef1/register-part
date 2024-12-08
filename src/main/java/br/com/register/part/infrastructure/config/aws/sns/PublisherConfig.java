package br.com.register.part.infrastructure.config.aws.sns;


import io.awspring.cloud.sns.core.SnsTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublisherConfig {

    @Bean
    public EmailSenderPublisher emailSenderPublisher(SnsTemplate snsTemplate,
                                                     SnsProperties snsProperties) {

        return new EmailSenderPublisher(snsTemplate, snsProperties);
    }
}
