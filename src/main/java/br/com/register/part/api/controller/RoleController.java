package br.com.register.part.api.controller;

import br.com.register.part.api.dto.RoleDto;
import br.com.register.part.domain.application.RoleApplication;
import br.com.register.part.domain.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/role")
@AllArgsConstructor
@RestController
public class RoleController {
    private final RoleApplication roleApplication;

    @PostMapping("/create")
    public ResponseEntity<Role> create(@RequestBody RoleDto dto) {
        return ResponseEntity.ok(roleApplication.create(dto.toDomain()));
    }
}
