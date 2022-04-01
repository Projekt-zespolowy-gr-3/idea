package pl.dmcs.idea.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.dmcs.idea.entities.Furniture;

import java.util.Optional;

@Transactional
public interface FurnitureRepository extends JpaRepository<Furniture, Long>, FurnitureIndexedRepository {

    Page<Furniture> findAll(Pageable pageable);
    Optional<Furniture> findByBusinessKeyAndAmountGreaterThanEqual(String businessKey, int quantity);
    Optional<Furniture> findByBusinessKey(String businessKey);
}
