package br.com.register.part.infrastructure.repository;

import br.com.register.part.domain.model.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaFileRepository extends CrudRepository<File, Long> {
    List<File> findByPartId(Long id);
}
