package br.com.register.part.api.controller;

import br.com.register.part.domain.application.PartApplication;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequestMapping("/v1/file")
@AllArgsConstructor
@Slf4j
public class PartFileController {
    private final PartApplication application;

    @PostMapping("/upload")
    public void uploadFile(@RequestParam MultipartFile files,
                           @RequestParam Long partId){

        this.application.saveFile(files, partId);
    }

    @GetMapping("/part/{id}")
    public ArrayList<Object> downloadFiles(@PathVariable Long id) {
        return this.application.findFileByPart(id);
    }
}
