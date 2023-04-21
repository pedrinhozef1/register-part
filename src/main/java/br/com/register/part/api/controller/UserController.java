package br.com.register.part.api.controller;

import br.com.register.part.api.dto.UserDto;
import br.com.register.part.domain.application.PartApplication;
import br.com.register.part.domain.model.User;
import br.com.register.part.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {
    private final PartApplication application;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody @Valid UserDto user){
        this.application.createUser(user);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(application.findById(id));
    }

    @PostMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        this.application.delete(id);
    }
}
