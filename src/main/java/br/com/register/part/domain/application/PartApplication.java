package br.com.register.part.domain.application;

import br.com.register.part.api.dto.UserDto;
import br.com.register.part.domain.model.File;
import br.com.register.part.domain.model.FileRepository;
import br.com.register.part.domain.model.Part;
import br.com.register.part.domain.model.User;
import br.com.register.part.domain.service.UserService;
import br.com.register.part.infrastructure.repository.JpaFileRepository;
import br.com.register.part.infrastructure.repository.JpaPartRepository;
import br.com.register.part.domain.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@AllArgsConstructor
public class PartApplication {
    private JpaPartRepository partRepository;
    private JpaFileRepository fIleRepository;
    private FileRepository fileRepository;
    private FileService fileService;
    private UserService userService;

    public void create(Part part) {
        partRepository.save(part);
    }

    public void saveFile(MultipartFile file, Long partId) {
        var fileId = fileRepository.uploadFile(file);
        fIleRepository.save(new File(partId, fileId));
    }

    public ArrayList<Object> findFileByPart(Long id){
        var files = this.fileService.findFileByPart(id);
        var response = new ArrayList<>();

        for (var file : files) {
            response.add(this.fileRepository.download(file.getFileId()));
        }

        return response;
    }

    public void createUser(UserDto user) {
        this.userService.create(user);
    }

    public User findById(Long id){
        return this.userService.findById(id);
    }

    public void delete(Long userId) {
        this.userService.delete(userId);
    }
}
