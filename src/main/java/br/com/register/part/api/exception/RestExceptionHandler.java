package br.com.register.part.api.exception;

import br.com.register.part.domain.model.exception.BusinessException;
import br.com.register.part.domain.model.exception.NotFoundException;
import br.com.register.part.infrastructure.InfrastructureException;
import br.com.register.part.infrastructure.integration.IntegrationBadRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(IntegrationBadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleIntegrationBadRequest(IntegrationBadRequest ex) {
      log.error(ex.getMessage());
        return Error.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Error handleNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage());
        return Error.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleBusinessException(BusinessException ex) {
        log.error(ex.getMessage());
        return Error.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ExceptionHandler(InfrastructureException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Error handleInfrastructureException(InfrastructureException ex) {
        log.error(ex.getMessage());
        return Error.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        List<String> messages = new ArrayList<>();
        for (var arg : ex.getFieldErrors()) {
            messages.add(arg.getDefaultMessage());
        }

        return Error.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(messages)
                .build();
    }
}
