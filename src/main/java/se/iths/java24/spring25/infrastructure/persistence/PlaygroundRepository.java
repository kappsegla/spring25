package se.iths.java24.spring25.infrastructure.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import se.iths.java24.spring25.domain.entity.Playground;

import java.util.List;

public interface PlaygroundRepository extends ListCrudRepository<Playground, Integer> {

    List<Playground> findByName(String name);

    @Query("SELECT p FROM Playground p WHERE p.name = :name")
        //@Query("select p from Playground  p where p.name = ?#{ authentication?.name }")
    List<Playground> findAllByName(String name);
}
