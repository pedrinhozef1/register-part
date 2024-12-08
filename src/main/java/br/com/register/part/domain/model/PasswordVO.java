package br.com.register.part.domain.model;

import br.com.register.part.domain.model.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
public class PasswordVO {
    private String value;

    public PasswordVO (String password) {
        if (Objects.isNull(password) || password.isEmpty()) {
            throw new BusinessException("A senha é obrigatória");
        }
        if (password.length() < 8) {
            throw new BusinessException("A senha deve conter no minimo 8 caracteres");
        }

        this.value = password;
    }
}
