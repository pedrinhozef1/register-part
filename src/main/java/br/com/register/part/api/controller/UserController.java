package br.com.register.part.api.controller;

import br.com.register.part.api.dto.UserDto;
import br.com.register.part.api.dto.UserUpdatedStatusDto;
import br.com.register.part.domain.application.PartApplication;
import br.com.register.part.domain.application.UserApplication;
import br.com.register.part.domain.model.ChangePassword;
import br.com.register.part.domain.model.EmailVO;
import br.com.register.part.domain.model.ResetPassword;
import br.com.register.part.infrastructure.entity.UserJpaEntity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final PartApplication application;
    private final UserApplication userApplication;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody @Valid UserDto user){
        this.userApplication.createUser(user.toDomain());
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody ForgotPasswordDto dto) {
        this.userApplication.forgotPassword(new EmailVO(dto.email()));
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordDto dto) {
        this.userApplication.resetPassword(ResetPassword.of(dto));
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordDto dto) {
        this.userApplication.changePassword(ChangePassword.of(dto));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserJpaEntity> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(application.findById(id));
    }

    @PostMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        this.application.delete(id);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserJpaEntity>> findAll(@RequestParam String companyDocument) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(application.findAll(companyDocument));
    }

    @PostMapping("/change-status/{id}/{status}")
    public ResponseEntity<UserUpdatedStatusDto> inactive(@PathVariable Long id, @PathVariable String status) {
        var updated = this.userApplication.changeStatus(id, status);

        return ResponseEntity.ok(updated);
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verify(@RequestParam(value = "code") String verificationCode) {
        return ResponseEntity.ok(userApplication.verifyUser(verificationCode));
    }

    public record ForgotPasswordDto(String email){}
    public record ResetPasswordDto(String token, String newPassword, String confirmNewPassword){}
    public record ChangePasswordDto(String email, String oldPassword, String newPassword, String confirmNewPassword){}
}
