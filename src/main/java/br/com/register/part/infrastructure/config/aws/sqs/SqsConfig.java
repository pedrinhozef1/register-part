package br.com.register.part.infrastructure.config.aws.sqs;

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.QueueNotFoundStrategy;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
@Log4j2
public class SqsConfig {
   public SqsConfig() {

   }

   @Bean
    SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient asyncClient) {
       return SqsMessageListenerContainerFactory.builder()
               .configure((options) -> options.acknowledgementMode(AcknowledgementMode.ON_SUCCESS)
                       .queueNotFoundStrategy(QueueNotFoundStrategy.FAIL))
               .sqsAsyncClient(asyncClient)
               .build();
   }
}
