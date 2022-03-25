package pl.dmcs.idea.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.dmcs.idea.entities.Furniture;

import java.util.Optional;

public interface FurnitureRepository extends JpaRepository<Furniture, Long> {
    Page<Furniture> findAll(Pageable pageable);

    Optional<Furniture> findByBusinessKeyAndAmountGreaterThanEqual(String businessKey, int quantity);
    Optional<Furniture> findByBusinessKey(String businessKey);
}
