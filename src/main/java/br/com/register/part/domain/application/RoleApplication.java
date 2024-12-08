package br.com.register.part.domain.application;

import br.com.register.part.domain.model.Role;
import br.com.register.part.domain.model.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class RoleApplication {
    private final RoleRepository roleRepository;

    public Role create (Role role) {
        return roleRepository.save(role);
    }
}
