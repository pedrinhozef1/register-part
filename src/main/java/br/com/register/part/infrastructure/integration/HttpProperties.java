package br.com.register.part.infrastructure.integration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Setter
@Slf4j
@ConfigurationProperties(value = "file-manager")
public class HttpProperties {
    private String baseUrl;
    private String upload;
    private String download;
    public WebClient getWebClient(){
        log.info("BASE URL: {}", this.getBaseUrl());

        return WebClient
                .create(this.getBaseUrl());
    }
}
