package br.com.register.part.api.exception;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Setter
@Builder
@Data
public class Error {
    private int statusCode;
    private List<String> message;
}
