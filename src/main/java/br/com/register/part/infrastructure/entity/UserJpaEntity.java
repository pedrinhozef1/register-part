package br.com.register.part.infrastructure.entity;

import br.com.register.part.domain.model.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Data
@Entity(name = "user")
@Builder
@AllArgsConstructor
public class UserJpaEntity {
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
    @NotNull(message = "RoleJpaEntity field cannot be null!")
    private RoleJpaEntity roleJpaEntity;

    @NotNull(message = "Created at field cannot be null!")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column
    private String verificationCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    private String birthDate;

    @Column
    @NotNull(message = "Created at field cannot be null!")
    private Status status;

    @Column
    @NotNull(message = "O CNPJ da empresa é obrigatório")
    private String companyDocument;

    public static UserJpaEntity of(User user) {
        UserJpaEntityBuilder userJpaEntityBuilder = UserJpaEntity.builder();

        userJpaEntityBuilder
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail().getAddress())
                .password(user.getPassword().getValue())
                .confirmPassword(user.getConfirmPassword().getValue())
                .phoneNumber(user.getPhoneNumber())
                .birthDate(user.getBirthDate())
                .roleJpaEntity(RoleJpaEntity.of(user.getRole()))
                .verificationCode(user.getVerificationCode())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .companyDocument(user.getCompany().getDocument());

        return userJpaEntityBuilder.build();
    }
    
    public User toDomain() {
        return User.builder()
                .id(this.id)
                .name(this.name)
                .username(this.username)
                .email(new EmailVO(this.email))
                .password(new PasswordVO(this.password))
                .confirmPassword(new ConfirmPasswordVO(this.confirmPassword))
                .phoneNumber(this.phoneNumber)
                .birthDate(this.birthDate)
                .role(this.roleJpaEntity.toDomain())
                .verificationCode(this.verificationCode)
                .status(this.status)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .company(Company.builder().document(this.companyDocument).build())
                .build();
    }
}
