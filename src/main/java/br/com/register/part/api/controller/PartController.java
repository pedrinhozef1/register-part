package br.com.register.part.api.controller;


import br.com.register.part.domain.application.PartApplication;
import br.com.register.part.infrastructure.entity.PartJpaEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/part")
@AllArgsConstructor
@Slf4j
public class PartController {
    private PartApplication application;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPart(@RequestBody PartJpaEntity partJpaEntity) {
        this.application.create(partJpaEntity);
    }

}
