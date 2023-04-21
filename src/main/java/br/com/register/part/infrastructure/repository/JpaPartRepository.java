package br.com.register.part.infrastructure.repository;

import br.com.register.part.domain.model.Part;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPartRepository extends CrudRepository<Part, Long> {
    Part findByName(String name);
}
