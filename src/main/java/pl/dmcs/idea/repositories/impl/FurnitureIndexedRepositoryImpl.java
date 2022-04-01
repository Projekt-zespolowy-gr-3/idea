package pl.dmcs.idea.repositories.impl;

import org.hibernate.search.mapper.orm.Search;
import org.springframework.data.domain.Pageable;
import pl.dmcs.idea.entities.Furniture;
import pl.dmcs.idea.repositories.FurnitureIndexedRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class FurnitureIndexedRepositoryImpl implements FurnitureIndexedRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Furniture> search(String query, Pageable pageable) {
        return Search.session(entityManager)
                .search(Furniture.class)
                .where(f -> f.match()
                        .fields("name", "description")
                        .matching(query)
                        .fuzzy())
                .fetchHits((int) pageable.getOffset(), pageable.getPageSize());
    }
}
