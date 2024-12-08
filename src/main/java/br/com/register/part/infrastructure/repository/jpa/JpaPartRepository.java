package br.com.register.part.infrastructure.repository.jpa;


import br.com.register.part.infrastructure.entity.PartJpaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPartRepository extends CrudRepository<PartJpaEntity, Long> {
}
