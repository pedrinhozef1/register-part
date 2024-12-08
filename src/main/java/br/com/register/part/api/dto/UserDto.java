package br.com.register.part.api.dto;


import br.com.register.part.domain.model.Company;
import br.com.register.part.domain.model.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotEmpty(message = "O nome do usuário é obrigatório")
    private String name;

    private String username;

    @NotEmpty(message = "A senha do usuário é obrigatória!")
    private String password;

    @NotEmpty(message = "A confirmação de senha é obrigatória")
    private String confirmPassword;

    private String phoneNumber;
    private String birthDate;

    @NotEmpty(message = "O e-mail do usuário é obrigatório")
    private String email;
    private String companyDocument;

    public User toDomain() {
        return User.of(this.name,
                this.email,
                this.username,
                this.password,
                this.confirmPassword,
                this.phoneNumber,
                this.birthDate,
                Company.builder()
                        .document(this.companyDocument).build());
    }
}
