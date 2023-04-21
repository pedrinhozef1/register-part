package br.com.register.part.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BusinessException extends RuntimeException{
    public BusinessException() {
        super();
    }

    public BusinessException(final String message) {
        super(message);
    }

    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
