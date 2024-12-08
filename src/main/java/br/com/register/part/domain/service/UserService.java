package br.com.register.part.domain.service;

import br.com.register.part.domain.model.Status;
import br.com.register.part.domain.model.User;
import br.com.register.part.infrastructure.entity.UserJpaEntity;
import br.com.register.part.domain.model.exception.NotFoundException;
import br.com.register.part.infrastructure.dto.AuthenticationUserDto;
import br.com.register.part.infrastructure.repository.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final JpaUserRepository userRepositoryJPA;
    private final CryptographyService cryptographyService;

    @Value(value = "${authentication-service-url}")
    private String authenticationServiceUrl;

    public Map<String, Object> authenticateUser(User user) {
        RestTemplate restTemplate = new RestTemplate();

        var decryptedPassword = cryptographyService.decrypt(user.getPassword().getValue());

        return restTemplate.postForObject(authenticationServiceUrl, new AuthenticationUserDto(user.getUsername(), user.getEmail().getAddress(), decryptedPassword), Map.class);
    }

    public UserJpaEntity findById(Long id) {
        return userRepositoryJPA.findById(id)
                .orElseThrow(() -> new NotFoundException("UserJpaEntity not found"));
    }

    public void delete(Long userId){
        var user = this.findById(userId);
        user.setStatus(Status.INATIVO);
        this.userRepositoryJPA.save(user);
    }

    public List<UserJpaEntity> findAll() {
        return userRepositoryJPA.findAll();
    }

    public List<UserJpaEntity> findAllByCompanyDocument(String companyDocument) {
        return userRepositoryJPA.findAllByCompanyDocument(companyDocument);
    }
}
