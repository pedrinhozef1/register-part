package br.com.register.part.infrastructure.repository.jpa;


import br.com.register.part.infrastructure.entity.FileJpaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaFileRepository extends CrudRepository<FileJpaEntity, Long> {
    List<FileJpaEntity> findByPartId(Long id);
}
