package br.com.register.part.domain.model;

import br.com.register.part.domain.model.exception.BusinessException;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Data
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sq_user")
    @SequenceGenerator(name = "sq_user", sequenceName = "sq_user", allocationSize = 1)
    private Long id;

    @NotEmpty(message = "Username field cannot be empty!")
    private String username;

    @NotEmpty(message = "Password field cannot be empty!")
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
    @NotNull(message = "Created at field cannot be null!")
    private Status status;

    @Builder
    public User(String username, String password, String confirmPassword, String email, Role role){
        if (username.length() < 6) {
            throw new BusinessException("Username field less than six characters!");
        }

        this.username = username;

        if (password.length() < 8) {
            throw new BusinessException("Password field less than eight characters!");
        } else if (confirmPassword.length() < 8) {
            throw new BusinessException("Confirm password field less than eight characters!");
        } else if (!password.equals(confirmPassword)){
            throw new BusinessException("Password field and confirm password field are different");
        }

        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.role = role;
        this.status = Status.ATIVO;
    }
}
