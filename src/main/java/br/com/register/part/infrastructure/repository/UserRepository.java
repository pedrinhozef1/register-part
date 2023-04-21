package br.com.register.part.infrastructure.repository;

import br.com.register.part.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,
        QuerydslPredicateExecutor<User> {
    Optional<User> findByUsername(String username);
}
