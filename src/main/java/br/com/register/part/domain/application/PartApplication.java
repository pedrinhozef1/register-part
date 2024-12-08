package br.com.register.part.domain.application;



import br.com.register.part.domain.model.FileRepository;
import br.com.register.part.domain.service.FileService;
import br.com.register.part.domain.service.UserService;
import br.com.register.part.infrastructure.entity.FileJpaEntity;
import br.com.register.part.infrastructure.entity.PartJpaEntity;
import br.com.register.part.infrastructure.entity.UserJpaEntity;
import br.com.register.part.infrastructure.repository.jpa.JpaFileRepository;
import br.com.register.part.infrastructure.repository.jpa.JpaPartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Component
@AllArgsConstructor
public class PartApplication {
    private JpaPartRepository partRepository;
    private JpaFileRepository fIleRepository;
    private FileRepository fileRepository;
    private FileService fileService;
    private UserService userService;

    public void create(PartJpaEntity partJpaEntity) {
        partRepository.save(partJpaEntity);
    }

    public void saveFile(MultipartFile file, Long partId) {
        var fileId = fileRepository.uploadFile(file);
        fIleRepository.save(new FileJpaEntity(partId, fileId));
    }

    public ArrayList<Object> findFileByPart(Long id){
        var files = this.fileService.findFileByPart(id);
        var response = new ArrayList<>();

        for (var file : files) {
            response.add(this.fileRepository.download(file.getFileId()));
        }

        return response;
    }

    public UserJpaEntity findById(Long id){
        return this.userService.findById(id);
    }

    public void delete(Long userId) {
        this.userService.delete(userId);
    }

    public List<UserJpaEntity> findAll(String companyDocument) {
        return userService.findAllByCompanyDocument(companyDocument);
    }
}
