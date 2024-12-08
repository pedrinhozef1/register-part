package br.com.register.part.api.dto;

import br.com.register.part.domain.model.Role;
import lombok.Data;

@Data
public class RoleDto {
    private String name;
    private String description;

    public Role toDomain() {
        return Role.builder()
                .name(this.name)
                .description(this.description)
                .build();
    }
}
