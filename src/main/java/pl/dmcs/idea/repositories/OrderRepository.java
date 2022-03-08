package pl.dmcs.idea.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dmcs.idea.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
