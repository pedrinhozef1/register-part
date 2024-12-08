package br.com.register.part.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdatedStatusDto {
    private Long id;
    private String name;
    private String email;
    private String status;
}
