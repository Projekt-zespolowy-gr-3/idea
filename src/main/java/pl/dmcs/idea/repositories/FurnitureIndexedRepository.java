package pl.dmcs.idea.repositories;

import org.springframework.data.domain.Pageable;
import pl.dmcs.idea.entities.Furniture;

import java.util.List;

public interface FurnitureIndexedRepository {

    List<Furniture> search(String query, Pageable pageable);
}
