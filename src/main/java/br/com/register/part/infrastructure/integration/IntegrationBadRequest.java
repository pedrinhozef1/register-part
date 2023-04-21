package br.com.register.part.infrastructure.integration;

public class IntegrationBadRequest extends RuntimeException {
    public IntegrationBadRequest(String message) {
        super(message);
    }
}
