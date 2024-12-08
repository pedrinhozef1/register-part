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
public class ChangePassword {
    private EmailVO email;
    private PasswordVO oldPassword;
    private PasswordVO newPassword;
    private ConfirmPasswordVO confirmNewPassword;

    public static ChangePassword of (UserController.ChangePasswordDto dto) {

        if (Objects.isNull(dto.oldPassword()) || dto.oldPassword().isEmpty()) {
            throw new BusinessException("A senha antiga é obrigatória");
        }

        return ChangePassword.builder()
                .email(new EmailVO(dto.email()))
                .oldPassword(new PasswordVO(dto.oldPassword()))
                .newPassword(new PasswordVO(dto.newPassword()))
                .confirmNewPassword(new ConfirmPasswordVO(dto.newPassword()))
                .build();
    }
}
