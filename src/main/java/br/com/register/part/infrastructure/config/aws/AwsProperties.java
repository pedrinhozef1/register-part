package br.com.register.part.infrastructure.config.aws;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AwsProperties {
    @Value(value = "${aws.endpoint}")
    private String endpoint;

    @Value(value = "${aws.region}")
    private String region;

    @Value(value = "${aws.access-key}")
    private String accessKey;

    @Value(value = "${aws.secret-key}")
    private String secretKey;
}
