package br.com.register.part.domain.application;

import br.com.register.part.api.dto.UserUpdatedStatusDto;
import br.com.register.part.domain.model.*;
import br.com.register.part.domain.model.exception.BusinessException;
import br.com.register.part.domain.service.CryptographyService;
import br.com.register.part.domain.service.RoleService;
import br.com.register.part.domain.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.core.token.Token;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Log4j2
public class UserApplication {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final CryptographyService cryptographyService;
    private final UserService userService;

    @Value(value = "${verify-user-callback-url}")
    private String verifyUserCallbackUrl;

    @Value(value = "${reset-password.redirect-url}")
    private String resetPasswordRedirectUrl;

    @Value(value = "${reset-password.server-integer}")
    private int resetPasswordServerInteger;

    private final SendEmailService emailSenderPublisher;

    @Transactional
    public User createUser(User user) {
        log.info("Cadastrando usuario");
        this.checkAccountAlreadyExists(user.getUsername(), user.getEmail().getAddress());
        var role = roleService.findRoleById(user.getDefaultRoles());

        user.addRole(role);
        user.encryptPassword(cryptographyService.encrypt(user.getPassword().getValue()),
                cryptographyService.encrypt(user.getConfirmPassword().getValue()));
        user.generateVerificationCode();

        var persistedUser = userRepository.createUser(user);

        log.info("Usuario cadastrado com sucesso");

        emailSenderPublisher.send(SendEmailCommand.buildVerifyAccountEmail(user, verifyUserCallbackUrl));

        log.info("Email de confirmação enviado");

        return persistedUser;
    }

    public void checkAccountAlreadyExists(String username, String email){
        Optional.ofNullable(this.userRepository.findByUsername(username))
                .ifPresent(user -> {
                    throw new BusinessException("O usuário solicitado já está em uso");
                });

        Optional.ofNullable(this.userRepository.findByEmail(email))
                .ifPresent(user -> {
                    throw new BusinessException("O email solicitado já está em uso");
                });

    }

    public Map<String, Object> verifyUser(String verificationCode) {
        log.info("Habilitando cadastro do usuário");
        var user = Optional.ofNullable(userRepository.findByVerificationCode(verificationCode))
                .orElseThrow(() -> new BusinessException("Código de verificação inválido ou o usuário já está ativo"));

        user.setStatus(Status.ATIVO);
        userRepository.update(user);

        log.info("Cadastro habilitado com sucesso");

        try {
            return authenticateUser(user);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException("Usuário habilitado com sucesso, erro ao autenticar usuário");
        }

    }

    private Map<String, Object> authenticateUser(User user) {
        log.info("Autenticando usuário");
        var authenticatedUser = userService.authenticateUser(user);
        log.info("Usuario autenticado com sucesso");

        return authenticatedUser;
    }

    public void forgotPassword(EmailVO email) {
        log.info("Buscando usuario com o email solicitado");

        var user = Optional.ofNullable(userRepository.findByEmail(email.getAddress()))
                .orElseThrow(() -> new BusinessException("Não foi encontrado nenhum usuário cadastrado com o e-mail utilizado"));

        log.info("Usuario encontrado");

        var tokenService = generateResetPasswordToken(user);
        Token token = tokenService.allocateToken(user.getEmail().getAddress());

        var emailPayload = SendEmailCommand.buildForgotPasswordEmail(user, token.getKey(), resetPasswordRedirectUrl);
        this.emailSenderPublisher.send(emailPayload);

        log.info("Email de redefinição de senha enviado");
    }

    public void changePassword(ChangePassword changePassword) {
        var user = userRepository.findByEmail(changePassword.getEmail().getAddress());

        var decryptedActualPassword = cryptographyService.decrypt(user.getPassword().getValue());

        if (Objects.equals(decryptedActualPassword, changePassword.getOldPassword())) {
            throw new BusinessException("A senha antiga está incorreta, verifique");
        }

        user.validatePassword(changePassword.getNewPassword().getValue(), changePassword.getConfirmNewPassword().getValue());

        var encryptedPassword = cryptographyService.encrypt(changePassword.getNewPassword().getValue());
        var encryptedConfirmPassword = cryptographyService.encrypt(changePassword.getConfirmNewPassword().getValue());

        user.encryptPassword(encryptedPassword, encryptedConfirmPassword);

        userRepository.update(user);
    }
    public void resetPassword(ResetPassword resetPassword) {
        var token = readToken(resetPassword.getToken());

        if (isExpired(token)) {
            throw new BusinessException("O token enviado para redefinir a senha está expirado");
        }

        var user = userRepository.findByEmail(token.getEmail().getAddress());

        KeyBasedPersistenceTokenService tokenService = generateResetPasswordToken(user);
        tokenService.verifyToken(resetPassword.getToken());

        user.validatePassword(resetPassword.getNewPassword().getValue(), resetPassword.getConfirmNewPassword().getValue());

        var encryptedPassword = cryptographyService.encrypt(resetPassword.getNewPassword().getValue());
        var encryptedConfirmPassword = cryptographyService.encrypt(resetPassword.getConfirmNewPassword().getValue());

        user.encryptPassword(encryptedPassword, encryptedConfirmPassword);

        userRepository.update(user);
    }

    public UserUpdatedStatusDto changeStatus(Long id, String status) {
        var user = userService.findById(id).toDomain();

        if (user.getStatus().equals(Status.VERIFY_EMAIL_PENDING)) {
            throw new BusinessException(String.format("O usuário %s está com a confirmação de e-mail pendente. Não é possível alterar o status.", user.getUsername()));
        }

        var tst = Boolean.valueOf(status);

        var newStatus = Boolean.TRUE.equals(tst) ? Status.ATIVO : Status.INATIVO;

        user.setStatus(newStatus);
        user.setUpdatedAt(LocalDateTime.now());
        var updatedUser = userRepository.update(user);

        log.info("Status do usuário {} atualizado para {} com sucesso", updatedUser.getUsername(), updatedUser.getStatus());

        return new UserUpdatedStatusDto(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail().getAddress(), updatedUser.getStatus().toString());
    }

    private boolean isExpired(ResetPasswordTokenData tokenData) {
        var createdAt = new Date(tokenData.getTimestamp()).toInstant();
        var dateTimeNow = new Date().toInstant();

        return createdAt.plus(Duration.ofMinutes(5)).isBefore(dateTimeNow);
    }

    @SneakyThrows
    protected KeyBasedPersistenceTokenService generateResetPasswordToken(User user) {
        KeyBasedPersistenceTokenService tokenService = new KeyBasedPersistenceTokenService();

        var decryptedPassword = cryptographyService.decrypt(user.getPassword().getValue());

        tokenService.setServerSecret(decryptedPassword);
        tokenService.setServerInteger(resetPasswordServerInteger);
        tokenService.setSecureRandom(new SecureRandomFactoryBean().getObject());

        return tokenService;
    }

    private ResetPasswordTokenData readToken(String rawToken) {
        String rawTokenDecoded = new String(Base64.getDecoder().decode(rawToken));
        String [] tokenParts = rawTokenDecoded.split(":");
        Long tokenTimestamp = Long.parseLong(tokenParts[0]);
        String email = tokenParts[2];

        return new ResetPasswordTokenData(new EmailVO(email), tokenTimestamp);
    }
}
