package br.com.register.part.domain.model;

import org.springframework.web.multipart.MultipartFile;

public interface FileRepository {
    Long uploadFile(MultipartFile file);

    String download(Long id);
}
