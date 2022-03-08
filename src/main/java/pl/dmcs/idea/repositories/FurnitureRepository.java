package pl.dmcs.idea.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dmcs.idea.entities.Furniture;

import java.util.Optional;

public interface FurnitureRepository extends JpaRepository<Furniture, Long> {

    Optional<Furniture> findByBusinessKey(String businessKey);
}
