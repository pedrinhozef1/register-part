package br.com.register.part.domain.model;

import br.com.register.part.api.controller.UserController;
import br.com.register.part.domain.model.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPassword {
    private String token;
    private PasswordVO oldPassword;
    private PasswordVO newPassword;
    private PasswordVO confirmNewPassword;

    public static ResetPassword of (UserController.ResetPasswordDto dto) {
        if (Objects.isNull(dto.token()) || dto.token().isEmpty()) {
            throw new BusinessException("O token para redefinir a senha é obrigatório e não foi enviado. Tente clicar novamente no link enviado via e-mail.");
        }

        if (Objects.isNull(dto.newPassword()) || dto.newPassword().isEmpty()) {
            throw new BusinessException("A senha nova é obrigatória");
        }

        if (Objects.isNull(dto.confirmNewPassword()) || dto.confirmNewPassword().isEmpty()) {
            throw new BusinessException("A confirmação da senha nova é obrigatória");
        }

        if (!Objects.equals(dto.newPassword(), dto.confirmNewPassword())) {
            throw new BusinessException("A confirmação da senha e a senha devem ser iguais");
        }

        return ResetPassword.builder()
                .token(dto.token())
                .newPassword(new PasswordVO(dto.newPassword()))
                .confirmNewPassword(new PasswordVO(dto.newPassword()))
                .build();
    }
}
