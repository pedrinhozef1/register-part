package br.com.register.part.infrastructure.repository;


import br.com.register.part.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RoleRepository extends JpaRepository<Role, Long>,
        QuerydslPredicateExecutor<Role> {

}
