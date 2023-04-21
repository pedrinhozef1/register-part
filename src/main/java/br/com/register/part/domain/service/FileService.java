package br.com.register.part.domain.service;

import br.com.register.part.domain.model.File;
import br.com.register.part.infrastructure.repository.JpaFileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileService {
    private JpaFileRepository repository;

    public List<File> findFileByPart(Long partId){
        return repository.findByPartId(partId);
    }
}
