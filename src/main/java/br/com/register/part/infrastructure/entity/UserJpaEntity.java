package br.com.register.part.infrastructure.entity;

import br.com.register.part.api.dto.UserDto;
import br.com.register.part.domain.model.Status;
import br.com.register.part.domain.model.exception.BusinessException;
import lombok.*;
import net.bytebuddy.utility.RandomString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Data
@Entity(name = "user")
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sq_user")
    @SequenceGenerator(name = "sq_user", sequenceName = "sq_user", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @NotEmpty(message = "Username field cannot be empty!")
    @Column
    private String username;

    @NotEmpty(message = "Password field cannot be empty!")
    @Column
    private String password;

    @NotEmpty(message = "Confirm password field cannot be empty!")
    @Column(name = "confirm_password")
    private String confirmPassword;

    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    @NotNull(message = "Role field cannot be null!")
    private Role role;

    @NotNull(message = "Created at field cannot be null!")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column
    private String verificationCode;

    @Column
    @NotNull(message = "Created at field cannot be null!")
    private Status status;

    public static User of(UserDto userDto) {
        User.UserBuilder userBuilder = builder();

        if (userDto.getUsername().length() < 6) {
            throw new BusinessException("O usuário deve conter no minimo 6 caracteres");
        }

        userBuilder.username(userDto.getUsername());

        if (userDto.getPassword().length() < 8) {
            throw new BusinessException("A senha deve conter no minimo 8 caracteres!");
        } else if (userDto.getConfirmPassword().length() < 8) {
            throw new BusinessException("A confirmação de senha deve conter no minimo 8 caracteres!");
        } else if (!userDto.getPassword().equals(userDto.getConfirmPassword())){
            throw new BusinessException("A senha e confirmar senha devem ser iguais!");
        }

        userBuilder.password(userDto.getPassword())
                .confirmPassword(userDto.getConfirmPassword())
                .email(userDto.getEmail())
                .createdAt(LocalDateTime.now())
                .status(Status.VERIFY_EMAIL_PENDING)
                .name(userDto.getName());

        return userBuilder.build();
    }

    public void addRole(Role role) {
        if (Objects.isNull(role)) {
            throw new BusinessException("A permissão do usuário é obrigatória");
        }

        this.role = role;
    }

    public void encryptPassword(String encryptedPassword, String encryptedConfirmPassword) {
        this.password = encryptedPassword;
        this.confirmPassword = encryptedConfirmPassword;
    }

    public void generateVerificationCode() {
        this.verificationCode = RandomString.make(64);
    }
}
