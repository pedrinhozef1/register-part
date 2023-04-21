package br.com.register.part.api.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    @NotEmpty(message = "Username field cannot be empty!")
    private String username;

    @NotEmpty(message = "Password field cannot be empty!")
    private String password;

    @NotEmpty(message = "Confirm password field cannot be empty!")
    @Column(name = "confirm_password")
    private String confirmPassword;

    private String email;

    @NotNull(message = "Role field cannot be null!")
    @Column(name = "role_id")
    private Long role;
}
