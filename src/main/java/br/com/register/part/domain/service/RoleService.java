package br.com.register.part.domain.service;

import br.com.register.part.domain.model.exception.NotFoundException;
import br.com.register.part.domain.model.Role;
import br.com.register.part.infrastructure.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {
    private RoleRepository repository;

    public Role findRoleById(Long id){
        return this.repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found"));
    }
}
