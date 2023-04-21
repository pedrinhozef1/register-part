package br.com.register.part.domain.service;

import br.com.register.part.api.dto.UserDto;
import br.com.register.part.domain.model.Status;
import br.com.register.part.domain.model.exception.BusinessException;
import br.com.register.part.domain.model.exception.NotFoundException;
import br.com.register.part.domain.model.User;
import br.com.register.part.infrastructure.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private RoleService roleService;
    private CryptographyService cryptographyService;

    public void create(UserDto user) {
        User newUser = User.builder()
                .username(user.getUsername())
                .password(this.cryptographyService.encrypt(user.getPassword()))
                .confirmPassword(this.cryptographyService.encrypt(user.getConfirmPassword()))
                .email(user.getEmail())
                .role(this.roleService.findRoleById(user.getRole()))
                .build();

        this.checkAccountAlreadyExists(user.getUsername());
        this.userRepository.save(newUser);
    }

    public void checkAccountAlreadyExists(String username){
        Optional<User> findUser = this.userRepository.findByUsername(username);

        if (findUser.isPresent()) {
            throw new BusinessException("An account with the username " + username + " already exists!");
        }
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public void delete(Long userId){
        var user = this.findById(userId);
        user.setStatus(Status.INATIVO);
        this.userRepository.save(user);
    }
}
