package br.com.register.part.domain.model;

import br.com.register.part.domain.model.exception.BusinessException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.util.codec.binary.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private Long id;
    private String name;
    private EmailVO email;
    private String username;

    private PasswordVO password;
    private ConfirmPasswordVO confirmPassword;
    private String phoneNumber;
    private String birthDate;
    private Role role;
    private String verificationCode;
    private Status status;
    private Company company;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Valid
    public static User of(String name,
                          String email,
                          String username,
                          String password,
                          String confirmPassword,
                          String phoneNumber,
                          String birthDate,
                          Company company) {
        if (username.length() < 6) {
            throw new BusinessException("O usuário deve conter no minimo 6 caracteres");
        }

        return User.builder()
                .name(validateField(name, "O nome é obrigatório"))
                .email(new EmailVO(email))
                .username(validateField(username, "O usuário é obrigatório"))
                .password(new PasswordVO(password))
                .confirmPassword(new ConfirmPasswordVO(confirmPassword))
                .phoneNumber(phoneNumber)
                .birthDate(birthDate)
                .createdAt(LocalDateTime.now())
                .status(Status.VERIFY_EMAIL_PENDING)
                .company(company)
                .build();
    }

    private static String validateField(String value, String errorMessage) {
        return Optional.ofNullable(value).orElseThrow(() -> new BusinessException(errorMessage));
    }

    public Long getDefaultRoles() {
        return 1L;
    }

    public void addRole(Role role) {
        this.role = role;
    }

    public void encryptPassword(String password, String confirmPassword) {
        this.password = new PasswordVO(password);
        this.confirmPassword = new ConfirmPasswordVO(confirmPassword);
    }

    public void validatePassword(String password, String confirmPassword) {
        this.password = new PasswordVO(password);
        this.confirmPassword = new ConfirmPasswordVO(confirmPassword);

        if (!this.password.getValue().equals(this.confirmPassword.getValue())) {
            throw new BusinessException("A senha e a confirmação de senha devem ser iguais");
        }
    }

    public void generateVerificationCode() {
        this.verificationCode = RandomStringUtils.random(40);
    }
}
