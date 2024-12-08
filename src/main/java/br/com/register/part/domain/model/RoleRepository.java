package br.com.register.part.domain.model;

public interface RoleRepository {
    Role findById(Long id);

    Role save(Role role);
}
