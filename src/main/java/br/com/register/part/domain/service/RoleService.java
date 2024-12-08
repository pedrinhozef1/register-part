package br.com.register.part.domain.service;

import br.com.register.part.domain.model.Role;
import br.com.register.part.domain.model.RoleRepository;
import br.com.register.part.domain.model.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {
    private RoleRepository roleRepository;

    public Role findRoleById(Long id){
        return Optional.ofNullable(this.roleRepository.findById(id))
                .orElseThrow(() -> new NotFoundException("Permissão solicitada não encontrada"));
    }
}
