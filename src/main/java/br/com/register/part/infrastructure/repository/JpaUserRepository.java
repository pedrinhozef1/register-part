package br.com.register.part.infrastructure.repository;

import br.com.register.part.infrastructure.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryJPA extends JpaRepository<UserJpaEntity, Long>,
        QuerydslPredicateExecutor<UserJpaEntity> {
    Optional<UserJpaEntity> findByUsername(String username);
    Optional<UserJpaEntity> findByEmail(String email);

    @Query(value = "SELECT * FROM register.user a WHERE a.verification_code = :verificationCode AND a.status = 3",
    nativeQuery = true)
    Optional<UserJpaEntity> findByVerificationCode(@Param("verificationCode") String verificationCode);
}
