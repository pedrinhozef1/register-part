package br.com.register.part.domain.service;

import br.com.register.part.infrastructure.entity.FileJpaEntity;
import br.com.register.part.infrastructure.repository.jpa.JpaFileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileService {
    private JpaFileRepository repository;

    public List<FileJpaEntity> findFileByPart(Long partId){
        return repository.findByPartId(partId);
    }
}
