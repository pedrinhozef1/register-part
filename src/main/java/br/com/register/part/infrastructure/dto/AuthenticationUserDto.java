package br.com.register.part.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationUserDto {
    private String username;
    private String email;
    private String password;
}
