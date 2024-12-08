package br.com.register.part.infrastructure.repository.jpa;


import br.com.register.part.infrastructure.entity.RoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface JpaRoleRepository extends JpaRepository<RoleJpaEntity, Long>,
        QuerydslPredicateExecutor<RoleJpaEntity> {

}
