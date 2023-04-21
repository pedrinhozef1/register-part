package br.com.register.part.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CryptographyException extends RuntimeException {
    public CryptographyException() {
        super();
    }

    public CryptographyException(final String message) {
        super(message);
    }

    public CryptographyException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
