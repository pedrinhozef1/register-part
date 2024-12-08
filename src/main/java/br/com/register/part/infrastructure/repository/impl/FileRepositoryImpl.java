package br.com.register.part.infrastructure.repository.impl;

import br.com.register.part.domain.model.FileRepository;
import br.com.register.part.infrastructure.integration.HttpClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@Component
@AllArgsConstructor
public class FileRepositoryImpl implements FileRepository {
    private final HttpClient client;

    public Long uploadFile(MultipartFile file){
        log.info("Salvando arquivo");
        return client.upload(file).getId();
    }

    public String download(Long id){
        return this.client.download(id).getUrl();
    }
}
