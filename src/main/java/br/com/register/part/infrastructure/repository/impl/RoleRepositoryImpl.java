package br.com.register.part.infrastructure.repository.impl;

import br.com.register.part.domain.model.Role;
import br.com.register.part.domain.model.RoleRepository;
import br.com.register.part.infrastructure.entity.RoleJpaEntity;
import br.com.register.part.infrastructure.repository.jpa.JpaRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final JpaRoleRepository jpaRoleRepository;

    public Role save(Role role) {
        var entity = RoleJpaEntity.of(role);

        return jpaRoleRepository.save(entity).toDomain();
    }

    @Override
    public Role findById(Long id) {
        return jpaRoleRepository.findById(id).map(RoleJpaEntity::toDomain).orElse(null);
    }
}
