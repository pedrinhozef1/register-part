package br.com.register.part.domain.model;

import br.com.register.part.domain.model.exception.BusinessException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ConfirmPasswordVO {
    private String value;

    public ConfirmPasswordVO(String password) {
        if (Objects.isNull(password) || password.isEmpty()) {
            throw new BusinessException("A confirmação de senha é obrigatória");
        }
        this.value = password;
    }
}
