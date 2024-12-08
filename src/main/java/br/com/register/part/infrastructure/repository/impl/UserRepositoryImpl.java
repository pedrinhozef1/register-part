package br.com.register.part.infrastructure.repository.impl;

import br.com.register.part.domain.model.User;
import br.com.register.part.infrastructure.entity.UserJpaEntity;
import br.com.register.part.domain.model.UserRepository;
import br.com.register.part.infrastructure.repository.jpa.JpaUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository userRepositoryJPA;

    public User createUser(User user) {
        var entity = UserJpaEntity.of(user);
        return userRepositoryJPA.save(entity).toDomain();

    }

    public User update(User user) {
        var entity = UserJpaEntity.of(user);
        return userRepositoryJPA.save(entity).toDomain();
    }

    public User findByUsername(String username) {
        return userRepositoryJPA.findByUsername(username).map(UserJpaEntity::toDomain).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepositoryJPA.findByEmail(email).map(UserJpaEntity::toDomain).orElse(null);
    }

    public User findByVerificationCode(String verificationCode) {
        return userRepositoryJPA.findByVerificationCode(verificationCode).map(UserJpaEntity::toDomain).orElse(null);
    }
}
