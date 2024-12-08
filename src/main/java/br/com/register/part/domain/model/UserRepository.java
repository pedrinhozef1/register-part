package br.com.register.part.domain.model;

import br.com.register.part.infrastructure.entity.UserJpaEntity;

public interface UserRepository {
    User createUser(User user);
    User findByUsername(String username);
    User findByEmail(String email);
    User findByVerificationCode(String verificationCode);
    User update(User user);
}
